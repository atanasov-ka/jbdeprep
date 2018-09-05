package org.vb.backend.jms;

import java.io.StringReader;
import java.util.ArrayList;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.vb.backend.dto.BoxRSDTO;
import org.vb.backend.dto.VerbRSDTO;
import org.vb.backend.jms.util.JMSConstants;
import org.vb.backend.jpa.dao.BoxDAO;
import org.vb.backend.jpa.service.BoxService;

/**
 * Message-Driven Bean implementation class for: ManualInsertDataBoxQueueMDB
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "vbManualInsertDataBoxList"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue")
		}, 
		mappedName = "vbManualInsertDataBoxList")
public class ManualInsertDataBoxQueueMDB implements MessageListener {
    
	@EJB
	private BoxDAO boxDAO;
	
	@EJB
	private BoxService boxService;
	
	public void onMessage(Message message) {
		try {
			String body = message.getBody(String.class);
			String username = message.getStringProperty(JMSConstants.VB_USER_NAME);
			String boxName = message.getStringProperty(JMSConstants.VB_IMPORT_BOX_NAME);
			String boxFront = message.getStringProperty(JMSConstants.VB_IMPORT_BOX_FRONT);
			String boxBack = message.getStringProperty(JMSConstants.VB_IMPORT_BOX_BACK);
			
			JsonReader reader = Json.createReader(new StringReader(body));
			JsonArray jsonArray = reader.readArray();
			BoxRSDTO box = new BoxRSDTO();
			box.setVerbList(new ArrayList<VerbRSDTO>());
			box.setName(boxName);
			box.setPublic(false);
			box.setFront(boxFront);
			box.setBack(boxBack);
			
			for (int i = 0; i < jsonArray.size(); ++i) {
				JsonObject obj = jsonArray.getJsonObject(i);
				
				VerbRSDTO verb = new VerbRSDTO();
				verb.setBack(obj.getJsonObject("back").getString("text"));
				verb.setBackAudio(obj.getJsonObject("back").getString("audio"));
				verb.setBackTranscription(obj.getJsonObject("back").getString("transcription"));
				
				verb.setFront(obj.getJsonObject("front").getString("text"));
				verb.setFrontAudio(obj.getJsonObject("front").getString("audio"));
				verb.setFrontTranscription(obj.getJsonObject("front").getString("transcription"));
				
				box.getVerbList().add(verb);
			}
			
			boxService.createBox(box, username);
			
		} catch (JMSException e) {
			e.printStackTrace();
		}        
    }
}
