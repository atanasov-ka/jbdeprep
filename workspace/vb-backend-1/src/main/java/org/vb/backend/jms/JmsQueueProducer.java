package org.vb.backend.jms;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jms.*;

import org.vb.backend.jms.util.JMSConstants;
import org.vb.backend.util.LoggerBean;

import java.util.Arrays;

@RequestScoped
public class JmsQueueProducer {
	@Resource(mappedName = "java:jboss/jms/queue/vbUserNotifications")
	private Queue vbUserNotificationsQueue;
	
	@Resource(mappedName = "java:jboss/jms/queue/vbManualInsertDataBoxList")
	private Queue vbManualInsertDataBoxListQueue;
	
	@Inject
	@JMSConnectionFactory("java:jboss/DefaultJMSConnectionFactory")
	transient JMSContext jmsContext;
	
	@EJB
	private LoggerBean loggerBean;
	
	public void notifyUser(Long userId, String messageTitle, String messageShort, String messageLong) {
		TextMessage message = jmsContext.createTextMessage();
		try {
			message.setLongProperty(JMSConstants.VB_USER_ID, userId);
			message.setStringProperty(JMSConstants.VB_NOTIFY_MESSAGE_TITLE, messageTitle);
			message.setStringProperty(JMSConstants.VB_NOTIFY_MESSAGE_SHORT, messageShort);
			message.setStringProperty(JMSConstants.VB_NOTIFY_MESSAGE_LONG, messageLong);
		} catch (JMSException e) {
			loggerBean.logMessage(String.format("%s - %s", e.getMessage(), e.getStackTrace()));
		}
		
		JMSProducer jmsProducer = jmsContext.createProducer();
		jmsProducer.send(vbUserNotificationsQueue, message);
	}
	
	public void manualInsertDataBoxList(String userName, String boxName, String boxFront, String boxBack, String data) {
		JMSProducer jmsProducer = jmsContext.createProducer();
		
		jmsProducer.setProperty(JMSConstants.VB_IMPORT_BOX_NAME, boxName);
		jmsProducer.setProperty(JMSConstants.VB_USER_NAME, userName);
		jmsProducer.setProperty(JMSConstants.VB_IMPORT_BOX_FRONT, boxFront);
		jmsProducer.setProperty(JMSConstants.VB_IMPORT_BOX_BACK, boxBack);
		jmsProducer.setAsync(new CompletionListener() {
            @Override
            public void onCompletion(Message message) {
                try {
                    loggerBean.logMessage("AMQ sent: " + message.getJMSMessageID().toLowerCase());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onException(Message message, Exception exception) {
                loggerBean.logMessage("Exception: " + exception.getMessage() + " " + Arrays.toString(exception.getStackTrace()));
            }
        });
		jmsProducer.send(vbManualInsertDataBoxListQueue, data);
	}
}
