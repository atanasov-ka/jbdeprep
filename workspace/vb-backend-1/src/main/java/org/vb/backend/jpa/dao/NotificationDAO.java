package org.vb.backend.jpa.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.vb.backend.jpa.pojos.Notification;
import org.vb.backend.jpa.pojos.User;

@Stateless
public class NotificationDAO {
	
	@PersistenceContext(unitName="vb-backend-1")
	private EntityManager entityManager;
	
	public void notifyUser(Long userId, String shortMessage, String longMessage) {
		Notification notification = new Notification();
		notification.setMessageShort(shortMessage);
		notification.setMessageLong(longMessage);
		
		User user = entityManager.find(User.class, userId);
		notification.setUser(user);
		entityManager.persist(notification);
	}
}
