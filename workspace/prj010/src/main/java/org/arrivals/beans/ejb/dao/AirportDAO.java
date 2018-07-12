package org.arrivals.beans.ejb.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class AirportDAO
 */
@Stateless
@LocalBean
public class AirportDAO {

//	@PersitenceContext
//	EntityManager em;
	
    public void updateState(String message) {
    	String [] messages = message.split(":");
    	int count = Integer.parseInt(messages[0].replace("[", "").replace("]", ""));
    	String state = messages[1].replace("[", "").replace("]", "");
    	
    	System.out.println(String.format("ID: %s, State: %s", count, state));
    	
    }

}
