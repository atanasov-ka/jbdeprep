package org.vb.backend.jpa.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.vb.backend.dto.BoxRSDTO;
import org.vb.backend.dto.DTOMapper;
import org.vb.backend.dto.VerbRSDTO;
import org.vb.backend.jpa.dao.BoxDAO;
import org.vb.backend.jpa.dao.UserDAO;
import org.vb.backend.jpa.pojos.Box;
import org.vb.backend.jpa.pojos.User;
import org.vb.backend.jpa.pojos.Verb;

@Stateless
public class BoxService {
	
	@EJB
	private BoxDAO boxDAO;
	
	@EJB
	private UserDAO userDAO;

	public BoxRSDTO getBoxById(Long id, String currentUser, boolean isAdmin) {
		Box box;
		if (isAdmin) {
			box = boxDAO.getBoxById(id);
		} else {
			box = boxDAO.getBoxById(id, currentUser);
		}
		return DTOMapper.getBoxDTO(box);
	}
	
	public List<VerbRSDTO> getVerbsByBoxId(Long id, String username, boolean isAdmin) {
		List<Verb> verbList = boxDAO.findVerbsByBoxId(id, username, isAdmin);
		List<VerbRSDTO> result = new LinkedList<>();
		for (Verb v : verbList) {
			result.add(DTOMapper.getVerbDTO(v));
		}
		return result;
	}

	public List<BoxRSDTO> getAll(String currentUser, boolean isAdmin) {
		List<Box> boxList;
		if (isAdmin) {
			boxList = boxDAO.getAll();
		} else {
			boxList = boxDAO.getAll(currentUser);
		}
		
		List<BoxRSDTO> boxRSDTOs = DTOMapper.getBoxDTOListOnly(boxList);
		return boxRSDTOs;
	}

	public BoxRSDTO updateBox(BoxRSDTO boxrsdto) {
		Box requested = DTOMapper.getBox(boxrsdto);
		Box response = boxDAO.updateBox(requested);
		
		return DTOMapper.getBoxDTO(response);
	}

	public boolean deleteBoxById(Long id) {
		return boxDAO.deleteBoxById(id);
	}

	public boolean deleteBoxByIdAndUser(Long id, String currentUser) {
		return boxDAO.deleteBoxByIdAndUser(id, currentUser);
	}

	public BoxRSDTO createBox(BoxRSDTO boxrsdto, String principalName) {
		User user = userDAO.findUserByUsername(principalName);
		assert (user != null);
		List<Verb> verbs = DTOMapper.getVerbList(boxrsdto.getVerbList());
		Box box = boxDAO.createBox(boxrsdto.getName(), boxrsdto.getFront(), boxrsdto.getBack(), boxrsdto.isPublic(), user, verbs, boxrsdto.getCategoryId());
		return DTOMapper.getBoxDTO(box);
	}


	public List<BoxRSDTO> getBoxListByGroupId(String username, boolean isAdmin, Long groupId) {
		List<Box> boxList = boxDAO.findBoxByGroupId(groupId, username, isAdmin);
		List<BoxRSDTO> result = new ArrayList<>();
		for (Box oneBox : boxList) {
			result.add(DTOMapper.getBoxDTO(oneBox));
		}
		return result;
	}
}
