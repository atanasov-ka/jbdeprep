package org.vb.backend.jpa.service;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.vb.backend.dto.BoxPlayRSDTO;
import org.vb.backend.dto.DTOMapper;
import org.vb.backend.dto.VerbPlayRSDTO;
import org.vb.backend.jms.JmsQueueProducer;
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
	
	@Inject
	private JmsQueueProducer jmsQueueProducer;
	
	public BoxPlayRSDTO findBoxPlayByBoxIdAndUser(Long id, String username) {
		List<Play> playList = playDAO.getPlayListByBoxIdAndUser(id, username);
		Box box = boxDAO.getBoxById(id, username);
		return DTOMapper.getBoxPlayDTO(playList, box);
	}

	public BoxPlayRSDTO updateVerbPlays(Long id, List<VerbPlayRSDTO> verbPlayList, String username) {
		List<Play> playList = playDAO.updatePlayListByBoxIdAndUser(id, username, verbPlayList);
		double totalCorrectFronts = 0;
		double totalCorrectBacks  = 0;
		double correctLevelFrontLow  = 0;
		double correctLevelFrontMid  = 0;
		double correctLevelFrontHigh = 0;
		double correctLevelBackLow   = 0;
		double correctLevelBackMid   = 0;
		double correctLevelBackHigh  = 0;
		
		for (Play play : playList) {
			totalCorrectBacks += play.getCorrectBacks();
			totalCorrectFronts += play.getCorrectFronts();
			
			switch (play.getCorrectBacks().intValue()) {
				case 3:
				case 2:
					correctLevelBackHigh++;
					break;
				case 1:
				case 0:
				case -1:
					correctLevelBackMid++;
					break;
				case -2:
				case -3:
					correctLevelBackLow++;
					break;
			}
			
			switch (play.getCorrectFronts().intValue()) {
				case 3:
				case 2:
					correctLevelFrontHigh++;
					break;
				case 1:
				case 0:
				case -1:
					correctLevelFrontMid++;
					break;
				case -2:
				case -3:
					correctLevelFrontLow++;
					break;
			}
		}

		double progressFront = Math.round((totalCorrectFronts / verbPlayList.size() / Play.MAX_CORRECTNESS_DEGREE) * 100.0);
		double progressBack  = Math.round((totalCorrectBacks / verbPlayList.size() / Play.MAX_CORRECTNESS_DEGREE) * 100.0);
		double levelBackHigh = Math.round(correctLevelBackHigh / verbPlayList.size() * 100);
		double levelBackMid  = Math.round(correctLevelBackMid / verbPlayList.size() * 100); 
		double levelBackLow  = Math.round(correctLevelBackLow / verbPlayList.size() * 100);
		double levelFrontMid  = Math.round(correctLevelFrontMid / verbPlayList.size() * 100); 
		double levelFrontHigh = Math.round(correctLevelFrontHigh / verbPlayList.size() * 100);
		double levelFrontLow  = Math.round(correctLevelFrontLow / verbPlayList.size() * 100);
		
		Box box = boxDAO.getBoxById(id);
		box.setProgressFront(progressFront);
		box.setProgressBack(progressBack);
		box.setLevelBackHigh(levelBackHigh);
		box.setLevelBackMid(levelBackMid);
		box.setLevelBackLow(levelBackLow);
		box.setLevelFrontHigh(levelFrontHigh);
		box.setLevelFrontMid(levelFrontMid);
		box.setLevelFrontLow(levelFrontLow);
		box.setLastPlayDate(new Date());
		boxDAO.updateBox(box);
		jmsQueueProducer.notifyUser(box.getUser().getId(), 
				"Congratulations!", 
				"Your box progress increased.", 
				"Your overal progress is now: " + (progressFront + progressBack) / 2 );
		return DTOMapper.getBoxPlayDTO(playList, box);
	}

	public void removeHistorOfBoxPlay(Long id, String username) {
		playDAO.removeHistoryOfBoxPlay(id, username);
		
	}

}
