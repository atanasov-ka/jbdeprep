package org.vb.backend.watcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.vb.backend.jms.JmsQueueProducer;

@Startup
@Singleton
public class ManualInsertDataWatcher {
	
	private static Logger LOGGER = Logger.getLogger(ManualInsertDataWatcher.class.getName());
	
	private static final String DIR = System.getProperty("vb.watcher.data.dir");
	
	@Inject
	private JmsQueueProducer queueProducer;
	
	@PostConstruct
	public void start() throws InterruptedException {
		Thread.sleep(10 * 1000);
		LOGGER.info("Scanning " + DIR);
		File directory = new File(DIR);
		if (directory.exists()) {
			File[] userDirList = directory.listFiles();
			LOGGER.info("Users found: " + userDirList.length);
			for (File userDir : userDirList){
	            if (userDir.isDirectory()){
	            	String username = userDir.getName();
	            	LOGGER.info("Processing user: " + username);
	            	File[] languageDirList = userDir.listFiles();
	        		LOGGER.info("Files found: " + languageDirList.length);
	        		
	            	for (File languageDir : languageDirList) {
	            		if (languageDir.isDirectory()) {
	            			LOGGER.info("Processing language: " + languageDir.getName());
	            			
	            			String[] langArr = languageDir.getName().split("_");
	            			String boxFront = langArr[0];
	    					String boxBack = langArr[1];
	            			
	            			File[] boxFileList = languageDir.listFiles();
	            			LOGGER.info("Boxes Found: " + boxFileList.length);
	            			
	            			for (File boxFile : boxFileList) {	
	            				try {
	            					String boxName = boxFile.getName();
	            					LOGGER.info("Processing Box: " + boxName);
	            					String content = new String(Files.readAllBytes(Paths.get(boxFile.getAbsolutePath())));

									Thread.sleep(15 * 1000);
			    					queueProducer.manualInsertDataBoxList(username, boxName, boxBack, boxFront, content);
			    				} catch (IOException e) {
			    					e.printStackTrace();
			    				} finally {
			    					//oneUserFile.delete();
			    				}
	            			}
	            		}
	            	}
	            }
	        }	
		}
	}
}
