package org.vb.backend.watcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class ManualInsertDataWatcher {
	
	public static Logger logger = Logger.getLogger(ManualInsertDataWatcher.class.getName());  
		
	@PostConstruct
	public void start() {
		logger.info("ManualInsertDataWatcher Started!");
		File directory = new File("~/vbData");
                
		File[] fList = directory.listFiles();
        for (File file : fList){
            if (file.isFile()){
                
                logger.info(file.getName());
                
                try {
					String content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
					
				} catch (IOException e) {
					
					e.printStackTrace();
				} finally {
			        //Files.move(Paths.get("/foo.txt"), Paths.get("bar.txt"), StandardCopyOption.REPLACE_EXISTING);
				}
            }
        }
	}
}
