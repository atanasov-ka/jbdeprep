package org.arrivals.beans.ejb.db;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import org.h2.jdbcx.JdbcDataSource;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * Session Bean implementation class H2Service
 */
@Singleton
@LocalBean
public class H2Service {

    @PostConstruct
    public void postConstruct() {
    	 System.out.println("Creating DB service and registring it in JNDI registry");
    	 
    	 JdbcDataSource ds = new JdbcDataSource();
    	 ds.setURL("jdbc:h2:˜/test");
    	 ds.setUser("sa");
    	 ds.setPassword("sa");
    	 Context ctx = new InitialContext();
    	 ctx.bind("jdbc/dsName", ds);
    }

}
