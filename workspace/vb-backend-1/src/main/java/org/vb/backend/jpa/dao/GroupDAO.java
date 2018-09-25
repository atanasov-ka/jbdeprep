package org.vb.backend.jpa.dao;

import java.util.List;

import javax.ejb.Stateless;

import org.vb.backend.jpa.pojos.BoxCategory;

@Stateless
public class GroupDAO {

	public List<BoxCategory> getAllGroups(String username, boolean isAdmin) {
		// TODO Auto-generated method stub
		return null;
	}

	public BoxCategory getGroup(String username, boolean isAdmin, Long groupId) {
		// TODO Auto-generated method stub
		return null;
	}

}
