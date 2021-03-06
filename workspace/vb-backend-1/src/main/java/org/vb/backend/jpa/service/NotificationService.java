package org.vb.backend.jpa.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.vb.backend.jpa.dao.NotificationDAO;
import org.vb.backend.util.LoggerBean;

@Stateless
public class NotificationService {
	
	@EJB
	private LoggerBean loggerBean;
	
	@EJB
	private NotificationDAO notificationDAO;
	
	public void saveNotification(Long userId, String title, String shortMessage, String longMessage) {
		loggerBean.logMessage(String.format("%s: %s, %s, %s", userId, title, shortMessage, longMessage));
		notificationDAO.notifyUser(userId, shortMessage, longMessage);
	}
}
