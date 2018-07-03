package org.airplane.jms;

import java.util.Date;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.TextMessage;
import javax.jms.Topic;

@RequestScoped
public class MessageSender {

	@Inject
	transient JMSContext jmsContext;
	
	@Resource(lookup = "java:jboss/jms/topic/planeEemergencyTopic")
	private Topic emergencyTopic;
	
	public void sendMayday() {
		TextMessage message = jmsContext.createTextMessage("MAYDAY at " + new Date());
		JMSProducer jmsProducer = jmsContext.createProducer();
		jmsProducer.send(emergencyTopic, message);
	}

}
