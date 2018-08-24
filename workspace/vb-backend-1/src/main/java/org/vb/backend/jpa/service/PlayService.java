package org.vb.backend.jpa.service;

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
		double totalCorrectBacks = 0;
		for (Play play : playList) {
			totalCorrectBacks += play.getCorrectBacks();
			totalCorrectFronts += play.getCorrectFronts();
		}
		//         ??? 100%              30                     10                             3
		//         ??? 50 %              15                     10                             3
		double progressFront = (totalCorrectFronts / verbPlayList.size() / Play.MAX_CORRECTNESS_DEGREE) * 100.0;
		double progressBack = (totalCorrectBacks / verbPlayList.size() / Play.MAX_CORRECTNESS_DEGREE) * 100.0;
		
		Box box = boxDAO.getBoxById(id);
		box.setProgressFront(progressFront);
		box.setProgressBack(progressBack);
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
