package org.vb.backend.jpa.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.vb.backend.jpa.pojos.Box;
import org.vb.backend.jpa.pojos.Verb;

@Stateless
public class BoxDAO {
	@PersistenceContext(unitName="vb-backend-1")  
	EntityManager entityManager; 

	public Box createBox(String name, String front, String back, boolean isPublic, String owner, List<Verb> verbList) {
		Box box = new Box();
		box.setBack(back);
		box.setFront(front);
		box.setBoxName(name);
		box.setOwner(owner);
		box.setPublic(isPublic);
		box.setVerbList(verbList);
		box.setCreated(new Date());
		
		entityManager.persist(box);
		return box;
	}

	public List<Box> getAll() {
		TypedQuery<Box> allBoxQuery = entityManager.createQuery("select b from Box b", Box.class);
		return allBoxQuery.getResultList();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Box getBoxById(Long id) {
		Box box = entityManager.find(Box.class, id);
		entityManager.detach(box);
		return box;
	}

	public Box updateBox(Box box) {
		Box foundBox = entityManager.find(Box.class, box.getId());
		if (foundBox == null)
			return null;
		
		foundBox.setFront(box.getFront());
		foundBox.setBack(box.getBack());
		foundBox.setBoxName(box.getBoxName());
		
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
		
		if (currentUser.equals(box.getOwner())) {
			entityManager.remove(box);
			return true;
		} else {
			return false;
		}
	}
}
