package org.vb.backend.jpa.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.vb.backend.dto.BoxRSDTO;
import org.vb.backend.dto.DTOMapper;
import org.vb.backend.jpa.dao.BoxDAO;
import org.vb.backend.jpa.pojos.Box;
import org.vb.backend.jpa.pojos.Verb;

@Stateless
public class BoxService {
	
	@EJB
	private BoxDAO boxDAO;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public BoxRSDTO getBoxById(Long id) {
		Box box = boxDAO.getBoxById(id);
		return DTOMapper.getBoxDTO(box);
	}

	public List<BoxRSDTO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public BoxRSDTO updateBox(BoxRSDTO boxrsdto) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean deleteBoxById(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteBoxByIdAndUser(Long id, String currentUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public BoxRSDTO createBox(BoxRSDTO boxrsdto, String principalName) {
		List<Verb> verbs = DTOMapper.getVerbList(boxrsdto.getVerbList());
		Box box = boxDAO.createBox(boxrsdto.getName(), boxrsdto.getFront(), boxrsdto.getBack(), boxrsdto.isPublic(), principalName, verbs);
		return DTOMapper.getBoxDTO(box);
	}

}
