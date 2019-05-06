package org.vb.backend.jpa.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.vb.backend.dto.DTOMapper;
import org.vb.backend.dto.GroupRSDTO;
import org.vb.backend.jpa.dao.GroupDAO;
import org.vb.backend.jpa.pojos.BoxCategory;

@Stateless
public class GroupService {

	@EJB
	private GroupDAO groupDAO;
	
	public List<GroupRSDTO> getAllGroups(String username, boolean isAdmin) {
		List<BoxCategory> boxCategoryList = groupDAO.getAllGroups(username, isAdmin);
		return DTOMapper.getGroupDTOList(boxCategoryList);
	}

	public GroupRSDTO getGroup(String username, boolean isAdmin, Long groupId) {
		BoxCategory boxCategory = groupDAO.getGroup(username, isAdmin, groupId);
		return DTOMapper.getGroupDTO(boxCategory);
	}

	public GroupRSDTO createCategory(String username, GroupRSDTO groupRsDto) {
		BoxCategory boxCategory = groupDAO.create(username, groupRsDto);
		return DTOMapper.getGroupDTO(boxCategory);
	}
}
