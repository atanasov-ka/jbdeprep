package org.vb.backend.jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.vb.backend.jms.util.JMSConstants;
import org.vb.backend.jpa.service.NotificationService;
import org.vb.backend.util.LoggerBean;

/**
 * Message-Driven Bean implementation class for: JmsQueueConsumer
 */
@MessageDriven(
		activationConfig = { 
				@ActivationConfigProperty(propertyName = "destination", propertyValue = "vbUserNotifications"), 
				@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
		}, 
		mappedName = "vbUserNotifications")
public class JmsQueueConsumer implements MessageListener {
	
	@EJB
	private NotificationService notificationService;
	
	@EJB
	private LoggerBean loggerBean;
	
    public void onMessage(Message message) {
    	Long userId;
    	String title;
    	String shortMessage;
    	String longMessage;
    	
		try {
			userId = message.getLongProperty(JMSConstants.VB_USER_ID);
			title = message.getStringProperty(JMSConstants.VB_NOTIFY_MESSAGE_TITLE);
	        shortMessage = message.getStringProperty(JMSConstants.VB_NOTIFY_MESSAGE_SHORT);
	        longMessage = message.getStringProperty(JMSConstants.VB_NOTIFY_MESSAGE_LONG);
	        
	        notificationService.saveNotification(userId, title, shortMessage, longMessage);
		} catch (JMSException e) {
			loggerBean.logMessage(String.format("%s - %s", e.getMessage(), e.getStackTrace()));
		}
    }

}
