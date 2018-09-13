package org.vb.backend.jpa.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.vb.backend.dto.DTOMapper;
import org.vb.backend.dto.GroupRSDTO;
import org.vb.backend.jpa.dao.GroupDAO;
import org.vb.backend.jpa.pojos.Group;

@Stateless
public class GroupService {

	@EJB
	private GroupDAO groupDAO;
	
	public List<GroupRSDTO> getAllGroups(String username, boolean isAdmin) {
		List<Group> groupList = groupDAO.getAllGroups(username, isAdmin);
		return DTOMapper.getGroupDTOList(groupList);
	}

	public GroupRSDTO getGroup(String username, boolean isAdmin, Long groupId) {
		Group group = groupDAO.getGroup(username, isAdmin, groupId);
		return DTOMapper.getGroupDTO(group);
	}

	
}
