package org.dep.beans;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

public class BeanProducer {
	@Produces
	@ApplicationScoped
	public MessageCounterBean produceMessageCounter() {
		System.out.println("==> Producing MessageCounter bean from Producer");
		return new MessageCounterBean();
	}
}
