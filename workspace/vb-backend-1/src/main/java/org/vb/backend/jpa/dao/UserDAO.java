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
	EntityManager entityManager;

	public User register(String username, boolean isAdmin, boolean isRegularUser) {
		
		User user = findUserByUsername(username);
		if (null == user) {
			user = createUser(username, isAdmin);
		}
		
		return user;
	}

	private User createUser(String username, boolean isAdmin) {
		User newUser = new User();
		newUser.setCreated(new Date());
		newUser.setUsername(username);
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
}
