package org.vb.backend.jpa.service;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * Session Bean implementation class PrepareDevData
 */
@Singleton
@Startup
public class PrepareDevData {

	@EJB
	private UserService userService;
	
	@PostConstruct
	public void init() {
		userService.register("admin", "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3", false);
		userService.register("user1", "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3", false);
		userService.register("user2", "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3", false);
		userService.register("user3", "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3", false);
		userService.register("user4", "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3", false);
	}    
}
