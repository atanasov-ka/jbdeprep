package org.vb.backend.jpa.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.vb.backend.jpa.dao.UserDAO;
import org.vb.backend.jpa.pojos.User;

@Stateless
public class UserService {
	@EJB
	private UserDAO userDAO;

	public User register(String userName, String hashedPassword, boolean isAdmin) {
		return userDAO.register(userName, hashedPassword, isAdmin);
	}

	public boolean authenticate(String userName, String hashedPassword) {
		User user = userDAO.findUserByUsername(userName);
		return hashedPassword.equals(user.getPassword());
	}
}
