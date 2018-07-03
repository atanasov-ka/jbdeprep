package org.dep.beans;

import javax.annotation.PostConstruct;

@javax.ejb.Singleton
public class LoggerBean {

	@PostConstruct
	private void postConstruct() {
		System.out.println("==> LoggerBean constructed!");
	}
	
	public void logMessage(String message) {
		System.out.println(message);
	}
}
