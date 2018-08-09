package org.vb.backend.jpa.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.vb.backend.dto.BoxPlayRSDTO;
import org.vb.backend.dto.DTOMapper;
import org.vb.backend.dto.VerbPlayRSDTO;
import org.vb.backend.jpa.dao.BoxDAO;
import org.vb.backend.jpa.dao.PlayDAO;
import org.vb.backend.jpa.pojos.Box;
import org.vb.backend.jpa.pojos.Play;

@Stateless
public class PlayService {

	@EJB
	private BoxDAO boxDAO;
	
	@EJB
	private PlayDAO playDAO;
	
	public BoxPlayRSDTO findBoxPlayByBoxIdAndUser(Long id, String username) {
		List<Play> playList = playDAO.getPlayListByBoxIdAndUser(id, username);
		Box box = boxDAO.getBoxById(id, username);
		return DTOMapper.getBoxPlayDTO(playList, box);
	}

	public void updateVerbPlays(Long id, List<VerbPlayRSDTO> verbPlayList, String username) {
		playDAO.updatePlayListByBoxIdAndUser(id, username, verbPlayList);
		
	}

	public void removeHistorOfBoxPlay(Long id, String username) {
		playDAO.removeHistoryOfBoxPlay(id, username);
		
	}

}
