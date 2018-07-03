package org.dep.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.dep.qualifiers.MessageCounter;

@MessageCounter
@ApplicationScoped
public class MessageCounterBean {
	
	@PostConstruct
	private void postConstruct() {
		System.out.println("==> Message counter constructed!");
	}
	
	public MessageCounterBean() {
		count = 0;
	}
	private int count;
	
	public int getCount() {
		count++;
		return count;
	}
}
