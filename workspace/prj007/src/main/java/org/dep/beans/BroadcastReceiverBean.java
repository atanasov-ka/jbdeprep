package org.dep.beans;

import javax.annotation.PostConstruct;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Message-Driven Bean implementation class for: BroadcastReceiverBean
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "planeEemergencyTopic"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Topic")
		}, 
		mappedName = "planeEemergencyTopic")
public class BroadcastReceiverBean implements MessageListener {
	
	@PostConstruct
	private void postConstruct() {
		System.out.println("==> MDB BroadcastReceiverBean constructed!");
	}
	
	@Inject
	private LoggerBean loggerBean;
    	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
        try {
			loggerBean.logMessage("Received: " +  message.getBody(String.class));
		} catch (JMSException e) {
			e.printStackTrace();
		}
    }

}
