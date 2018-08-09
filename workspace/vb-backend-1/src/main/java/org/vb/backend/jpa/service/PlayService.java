package org.vb.backend.jpa.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.vb.backend.dto.BoxPlayRSDTO;
import org.vb.backend.dto.DTOMapper;
import org.vb.backend.jpa.dao.BoxDAO;
import org.vb.backend.jpa.dao.PlayDAO;
import org.vb.backend.jpa.dao.UserDAO;
import org.vb.backend.jpa.pojos.Box;
import org.vb.backend.jpa.pojos.Play;

@Stateless
public class PlayService {

	@EJB
	private BoxDAO boxDAO;
	
	@EJB
	private UserDAO userDAO;
	
	@EJB
	private PlayDAO playDAO;
	
	public BoxPlayRSDTO findBoxPlayByBoxIdAndPrincipal(Long id, String username) {
		List<Play> playList = playDAO.getPlayListByBoxIdAndUser(id, username);
		Box box = boxDAO.getBoxById(id, username);
		return DTOMapper.getBoxPlayDTO(playList, box);
	}

}
