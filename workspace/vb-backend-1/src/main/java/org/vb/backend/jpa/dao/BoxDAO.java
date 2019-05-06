package org.vb.backend.jpa.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.*;

import org.vb.backend.jpa.pojos.*;

@Stateless
public class BoxDAO {
	@PersistenceContext(unitName="vb-backend-1")  
	private EntityManager entityManager; 

	public Box createBox(String name, String front, String back, boolean isPublic, User owner, List<Verb> verbList) {
		Language langFront = getLanguageByAbbreviation(front);
		Language langBack = getLanguageByAbbreviation(back);

		Box box = new Box();
		box.setBack(langBack);
		box.setFront(langFront);
		box.setBoxName(name);
		box.setUser(owner);
		box.setPublic(isPublic);
		box.setCreated(new Date());
		BoxCategory category = getBoxCategory(owner, "default");
		box.setCategory(category);
		for (Verb v : verbList) {
		    box.addVerb(v);
        }
		entityManager.persist(box);
		return box;
	}

	private BoxCategory getBoxCategory(User owner, String categoryName) {
		TypedQuery<BoxCategory> boxCategoryQuery = entityManager.createNamedQuery("findCategoryByUserAndName",
				BoxCategory.class);
		boxCategoryQuery.setParameter("userId", owner.getId());
		boxCategoryQuery.setParameter("categoryName", categoryName);
		List<BoxCategory> boxCategoryList = boxCategoryQuery.getResultList();
		if (boxCategoryList.isEmpty()) {
			BoxCategory newCategory = new BoxCategory();
			newCategory.setName(categoryName);
			newCategory.setUser(owner);
			entityManager.persist(newCategory);
			return newCategory;
		} else {
			return boxCategoryList.get(0);
		}
	}

	private Language getLanguageByAbbreviation(String abbr) {
		abbr = abbr.toLowerCase();

		TypedQuery<Language> queryLangFront = entityManager.createNamedQuery("findLanguageByAbbreviation", Language.class);
		queryLangFront.setParameter("abbr", abbr);
		List<Language> result = queryLangFront.getResultList();
		
		if (result.isEmpty()) {
			Language newLang = new Language();
			newLang.setAbbreviation(abbr);
			entityManager.persist(newLang);
			return newLang;
		}
		
		assert(result.size() == 1);
		return result.get(0);
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

	public List<Verb> findVerbsByBoxId(Long id, String username, boolean isAdmin) {
		TypedQuery<Verb> query;
		if (isAdmin) {
			query = entityManager.createQuery("select v from Verb v where v.box.id = :id order by v.id asc", Verb.class);
		} else {
			query = entityManager.createQuery("select v from Verb v where v.box.id = :id and v.box.user.username = :username order by v.id asc", Verb.class);
			query.setParameter("username", username);
		}

		query.setParameter("id", id);
		return query.getResultList();
	}

	public List<Box> findBoxByGroupId(Long groupId, String username, boolean isAdmin) {
		TypedQuery<Box> query;
		if (isAdmin) {
			query = entityManager.createQuery("select b from Box b where b.boxCategory.id = :groupId order by b.id asc", Box.class);

		} else {
			query = entityManager.createQuery("select b from Box b where b.boxCategory.id = :groupId and b.user = :username order by b.id asc", Box.class);
			query.setParameter("username", username);
		}

		query.setParameter("groupId", groupId);
		return query.getResultList();
	}
}
