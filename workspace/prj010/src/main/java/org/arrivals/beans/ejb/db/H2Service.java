package org.arrivals.beans.ejb.db;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import org.h2.jdbcx.JdbcDataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Session Bean implementation class H2Service
 */
@Singleton
@LocalBean
public class H2Service {

    @PostConstruct
    public void postConstruct() throws NamingException {
    	 System.out.println("Creating DB service and registring it in JNDI registry");
    	 
    	 JdbcDataSource ds = new JdbcDataSource();
    	 ds.setURL("jdbc:h2:˜/airport");
    	 ds.setUser("sa");
    	 ds.setPassword("sa");
    	 Context ctx = new InitialContext();
    	 ctx.bind("jboss/datasources/airportDS", ds);
    }

}
