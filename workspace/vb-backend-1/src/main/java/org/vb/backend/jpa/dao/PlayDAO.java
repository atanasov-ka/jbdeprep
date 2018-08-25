package org.vb.backend.jpa.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.vb.backend.dto.VerbPlayRSDTO;
import org.vb.backend.exceptions.VBInternalException;
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
		TypedQuery<Play> playQuery = entityManager.createQuery("select p from Play p where p.user.username = :username and p.verb.boxId = :boxId order by p.correctBacks, p.correctFronts", Play.class);
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

	public List<Play> updatePlayListByBoxIdAndUser(Long id, String username, List<VerbPlayRSDTO> verbPlayList) {
		List<Play> playList = findPlaysForBox(id, username);
		for (VerbPlayRSDTO pDto : verbPlayList) {
			Play target = findPlayByID(pDto.getId(), playList);
			modifyCounters(target, pDto.getCorrectFronts(), pDto.getCorrectBacks());
			entityManager.persist(target);
		}
		return playList;
	}

	private void modifyCounters(Play target, Long correctFronts, Long correctBacks) {
		if (correctFronts >= 0) {
			for (int i = 0; i < correctFronts; i++) {
				target.addCorrectFront();
			}
		} else {
			for (long i = correctFronts; i < 0; i++) {
				target.addWrongFront();
			}
		}
		
		if (correctBacks >= 0) {
			for (int i = 0; i < correctBacks; i++) {
				target.addCorrectBack();
			}
		} else {
			for (long i = correctBacks; i < 0; i++) {
				target.addWrongBack();
			}
		}
	}

	private Play findPlayByID(Long playId, List<Play> playList) {
		for (int i = 0; i < playList.size(); ++i) {
			if (playList.get(i).getId().equals(playId)) {
				return playList.get(i);
			}
		}
		throw new VBInternalException("Can't find play with ID: " + playId);
	}

	public void removeHistoryOfBoxPlay(Long id, String username) {
		TypedQuery<Play> playQuery = entityManager.createQuery("delete from Play p where p.user.username = :username and p.verb.boxId = :boxId order by p.correctBacks, p.correctFonts", Play.class);
		playQuery.setParameter("username", username);
		playQuery.setParameter("boxId", id);
		playQuery.executeUpdate();
	}
}
