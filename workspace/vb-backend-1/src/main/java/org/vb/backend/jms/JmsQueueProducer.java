package org.vb.backend.jms;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.TextMessage;

import org.vb.backend.jms.util.JMSConstants;
import org.vb.backend.util.LoggerBean;

@RequestScoped
public class JmsQueueProducer {
	@Resource(mappedName = "java:jboss/jms/queue/vbUserNotifications")
	private Queue vbUserNotificationsQueue;
	
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
}