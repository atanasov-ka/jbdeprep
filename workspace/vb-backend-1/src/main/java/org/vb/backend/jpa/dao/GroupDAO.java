package org.vb.backend.jpa.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.vb.backend.jpa.pojos.BoxCategory;

@Stateless
public class GroupDAO {

	@PersistenceContext(unitName="vb-backend-1")
	private EntityManager entityManager;

	public List<BoxCategory> getAllGroups(String username, boolean isAdmin) {
		TypedQuery<BoxCategory> allCategoryQuery;
		if (isAdmin) {
			allCategoryQuery = entityManager.createQuery("select b from BoxCategory b order by b.name", BoxCategory.class);
		} else {
			allCategoryQuery = entityManager.createQuery("select b from BoxCategory b where b.user.username = :username order by b.name", BoxCategory.class);
			allCategoryQuery.setParameter("username", username);
		}

		return allCategoryQuery.getResultList();
	}

	public BoxCategory getGroup(String username, boolean isAdmin, Long groupId) {
		// TODO Auto-generated method stub
		return null;
	}

}
