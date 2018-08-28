package org.vb.backend.watcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Asynchronous;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;


/**
 * Session Bean implementation class ManualInsertDataWatcher
 */
@Singleton
@LocalBean
public class ManualInsertDataWatcher {
	
	public static Logger logger = Logger.getLogger(ManualInsertDataWatcher.class.getName());  
    private WatchService watcher;  
    private Path dir; 
	
    public ManualInsertDataWatcher() {
        dir = Paths.get("C:\\EX183\\jbdeprep\\workspace\\vbData");
    }

	public void stop() throws IOException {
		watcher.close();
	}
	
	@Asynchronous
	public void start() {
		WatchKey key = null;  
		String fileName = null;  
  
        while (true) {  
  
        	logger.log(Level.INFO, "Waiting for watch event");  
        	logger.log(Level.INFO, "Path being watched: " + dir.toString());  
  
            try {  
                key = watcher.take();  
            } catch (InterruptedException e) {  
                logger.log(Level.SEVERE, "Watcher got interrupted");  
                e.printStackTrace();  
            } catch (ClosedWatchServiceException c) {  
            	logger.log(Level.INFO, "Watchservice is closed exiting thread.");  
                break;  
            }  
  
            if (key != null) {  
  
                for (WatchEvent<?> event : key.pollEvents()) {  
                    WatchEvent.Kind<?> kind = event.kind();  
  
                    if (kind == StandardWatchEventKinds.OVERFLOW) {  
                        continue;  
                    }  
  
                    fileName = key.watchable().toString() + "\\" + event.context().toString();  
                    if (!key.reset()) {  
                    	logger.log(Level.SEVERE, "Watcher key could not be reset stopping watchservice.");  
                        break;  
                    }  
                }  
            }  
        }  
	}

}
