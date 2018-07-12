package org.arrivals.beans.jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.arrivals.beans.ejb.dao.AirportDAO;

/**
 * Message-Driven Bean implementation class for: AirportReceiver
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "airportReceiveQueue"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue")
		}, 
		mappedName = "airportReceiveQueue")
public class AirportReceiver implements MessageListener {

	@Inject
	private AirportDAO airportDAO;
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
    	try {
    		String me = message.getBody(String.class);
			airportDAO.updateState(me);
		} catch (JMSException e) {
			e.printStackTrace();
		}
        
    }

}
