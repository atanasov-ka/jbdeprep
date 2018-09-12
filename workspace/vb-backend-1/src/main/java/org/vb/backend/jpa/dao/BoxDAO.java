package org.vb.backend.jpa.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.vb.backend.jpa.pojos.Box;
import org.vb.backend.jpa.pojos.User;
import org.vb.backend.jpa.pojos.Verb;

@Stateless
public class BoxDAO {
	@PersistenceContext(unitName="vb-backend-1")  
	private EntityManager entityManager; 

	public Box createBox(String name, String front, String back, boolean isPublic, User owner, List<Verb> verbList) {
		Box box = new Box();
		box.setBack(back);
		box.setFront(front);
		box.setBoxName(name);
		box.setUser(owner);
		box.setPublic(isPublic);
		box.setCreated(new Date());
		
		if (null != verbList) {
			box.setVerbList(verbList);
		}
		entityManager.persist(box);
		
		return box;
	}

	public List<Box> getAll(String currentUser) {
		TypedQuery<Box> allBoxQuery = entityManager.createQuery("select b from Box b where b.user.username = :owner order by b.lastPlayDate desc, b.created asc", Box.class);
		allBoxQuery.setParameter("owner", currentUser);
		return allBoxQuery.getResultList();
	}
	
	public List<Box> getAll() {
		TypedQuery<Box> allBoxQuery = entityManager.createQuery("select b from Box b order by b.lastPlayDate desc, b.created desc", Box.class);
		return allBoxQuery.getResultList();
	}

	public Box getBoxById(Long id) {
		Box box = entityManager.find(Box.class, id);
		return box;
	}
	
	public Box getBoxById(Long id, String currentUser) {
		TypedQuery<Box> query = entityManager.createQuery("select b from Box b where b.id = :id and b.user.username = :owner", Box.class);
		query.setParameter("id", id);
		query.setParameter("owner", currentUser);
		
		Box result;
		try {
			result = query.getSingleResult();	
		} catch (NoResultException e) {
			return null;
		}
		return result;
	}

	public Box updateBox(Box box) {
		Box foundBox = entityManager.find(Box.class, box.getId());
		if (foundBox == null)
			return null;
		
		foundBox.setFront(box.getFront());
		foundBox.setBack(box.getBack());
		foundBox.setBoxName(box.getBoxName());
		
		if (box.getProgressFront() != null) {
			foundBox.setProgressFront(box.getProgressFront());
		}
		
		if (box.getProgressBack() != null) {
			foundBox.setProgressBack(box.getProgressBack());
		}
		
		entityManager.persist(foundBox);
		return foundBox;
	}

	public boolean deleteBoxById(Long id) {
		Box box = getBoxById(id);
		if (null == box) {
			return false;
		}
		
		entityManager.remove(box);
		return true;
	}
	
	public boolean deleteBoxByIdAndUser(Long id, String currentUser) {
		Box box = getBoxById(id);
		if (null == box) {
			return false;
		}
		
		if (currentUser.equals(box.getUser().getUsername())) {
			entityManager.remove(box);
			return true;
		} else {
			return false;
		}
	}

	public List<Verb> findVerbsByBoxId(Long id) {
		TypedQuery<Verb> query = entityManager.createQuery("select v from Verb v where v.boxId = :id", Verb.class);
		query.setParameter("id", id);
		return query.getResultList();
	}
}
