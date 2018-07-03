/**
 * 
 */
package org.dep.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.TextMessage;

/**
 * @author Estafet
 *
 */
@SessionScoped
public class MessageSender implements Serializable {

	@PostConstruct
	private void postConstruct() {
		System.out.println("==> Message Sender constructed!");
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 6957079486519383448L;
	
	@Resource(mappedName = "java:jboss/jms/queue/airportReceiveQueue")
	private Queue queue;
	
	@Inject
	@JMSConnectionFactory("java:jboss/DefaultJMSConnectionFactory")
	transient JMSContext jmsContext;
	
	public void sendToAirport(String text) {
		TextMessage message = jmsContext.createTextMessage(text);
		
		JMSProducer jmsProducer = jmsContext.createProducer();
		
		jmsProducer.send(queue, message);
	}

}
