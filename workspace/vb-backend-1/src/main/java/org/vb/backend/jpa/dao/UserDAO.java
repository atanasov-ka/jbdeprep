package org.vb.backend.jpa.dao;

import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.vb.backend.jpa.pojos.User;

@Stateless
public class UserDAO {
	@PersistenceContext(unitName="vb-backend-1")  
	private EntityManager entityManager;

	private User createUser(String username, String password, boolean isAdmin) {
		User newUser = new User();
		newUser.setCreated(new Date());
		newUser.setUsername(username);
		newUser.setPassword(password);
		newUser.setRole(isAdmin ? "admin" : "user" );
		
		entityManager.persist(newUser);
		return newUser;
	}

	public User findUserByUsername(String principalName) {
		TypedQuery<User> query = entityManager.createQuery("select u from User u where u.username = :username", User.class);
		query.setParameter("username", principalName);
		
		User user;
		try {
			user = query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return user;
	}

	public User register(String userName, String hashedPassword, boolean isAdmin) {
		User user = findUserByUsername(userName);
		if (user == null) {
			return createUser(userName, hashedPassword, isAdmin);
		} else {
			return null;
		}
	}
}
