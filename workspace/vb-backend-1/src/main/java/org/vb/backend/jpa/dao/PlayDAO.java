package org.vb.backend.jpa.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.vb.backend.jpa.pojos.Box;
import org.vb.backend.jpa.pojos.Play;
import org.vb.backend.jpa.pojos.User;
import org.vb.backend.jpa.pojos.Verb;

@Stateless
public class PlayDAO {
	@PersistenceContext(unitName="vb-backend-1")
	EntityManager entityManager;
	
	public List<Play> getPlayListByBoxIdAndUser(Long id, String username) {
		List<Play> playList;
		playList = findPlaysForBox(id, username);
		
		if (playList.isEmpty()) {
			// let's create them!
			createPlaysForBox(id, username);
			entityManager.flush();
			playList = findPlaysForBox(id, username);
		}
		
		return playList;
	}

	private List<Play> findPlaysForBox(Long id, String username) {
		List<Play> playList;
		TypedQuery<Play> playQuery = entityManager.createQuery("select p from Play p where p.user.username = :username and p.verb.boxId = :boxId", Play.class);
		playQuery.setParameter("username", username);
		playQuery.setParameter("boxId", id);
		playList = playQuery.getResultList();
		return playList;
	}

	private void createPlaysForBox(Long id, String username) {
		TypedQuery<Box> boxQuery = entityManager.createQuery("select b from Box b where b.user.username = :username and b.id = :boxId", Box.class);
		boxQuery.setParameter("username", username);
		boxQuery.setParameter("boxId", id);
		Box box = boxQuery.getSingleResult();
		
		TypedQuery<User> userQuery = entityManager.createQuery("select u from User u where u.username = :username", User.class);
		userQuery.setParameter("username", username);
		User user = userQuery.getSingleResult();
		
		for (Verb v : box.getVerbList()) {
			Play play = new Play();
			play.setVerb(v);
			play.setUser(user);
			entityManager.persist(play);
		}
	}
}
