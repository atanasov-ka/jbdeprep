package org.vb.backend.watcher;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

/**
 * Session Bean implementation class WatcherStopper
 */
@Singleton
@LocalBean
@Lock(LockType.READ)
public class WatcherStopper {

	@EJB
	private ManualInsertDataWatcher dataWatcher; 
    
	@PostConstruct
	private void onConstruct() {
		dataWatcher.start();
	}
	
	@PreDestroy
	private void stopWatcher(){
		try {
			dataWatcher.stop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
