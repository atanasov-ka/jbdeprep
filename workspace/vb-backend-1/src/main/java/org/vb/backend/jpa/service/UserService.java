package org.vb.backend.jpa.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.vb.backend.jpa.dao.UserDAO;
import org.vb.backend.jpa.pojos.User;

@Stateless
public class UserService {
	@EJB
	private UserDAO userDAO;

	public Long register(String username, boolean isAdmin, boolean isRegularUser) {
		User user = userDAO.register(username, isAdmin, isRegularUser);
		return user.getId();
	}
}
