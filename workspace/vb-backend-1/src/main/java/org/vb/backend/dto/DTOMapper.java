package org.vb.backend.dto;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.vb.backend.jpa.pojos.Box;
import org.vb.backend.jpa.pojos.Play;
import org.vb.backend.jpa.pojos.Verb;

public class DTOMapper {

	public static List<BoxRSDTO> getBoxDTOList(List<Box> boxList) {
		List<BoxRSDTO> result = new ArrayList<>();
		
		for (Box box : boxList) {
			result.add(DTOMapper.getBoxDTO(box));
		}
		
		return result;
	}

	public static BoxRSDTO getBoxDTO(Box box) {
		if (box == null) {
			return null;
		}
		
		BoxRSDTO boxRSDTO = getBoxDTOOnly(box);
		boxRSDTO.setVerbList(DTOMapper.getVerbDTOList(new ArrayList<>(box.getVerbList())));
		return boxRSDTO;
	}

	private static BoxRSDTO getBoxDTOOnly(Box box) {
		if (box == null) {
			return null;
		}
		
		BoxRSDTO boxRSDTO = new BoxRSDTO();
		
		boxRSDTO.setId(box.getId());
		boxRSDTO.setName(box.getBoxName());
		boxRSDTO.setBack(box.getBack());
		boxRSDTO.setFront(box.getFront());
		if (box.getUser() != null) {
			boxRSDTO.setOwner(box.getUser().getUsername());	
		}
		boxRSDTO.setPublic(box.isPublic());
		boxRSDTO.setCreated(box.getCreated());
		boxRSDTO.setProgressFront(box.getProgressFront());
		boxRSDTO.setProgressBack(box.getProgressBack());
		if (null == box.getVerbList()) {
			boxRSDTO.setVerbCount(0);
		} else {
			boxRSDTO.setVerbCount(box.getVerbList().size());
		}
			
		return boxRSDTO;
	}
	
	public static List<VerbRSDTO> getVerbDTOList(List<Verb> verbList) {
		List<VerbRSDTO> result = new LinkedList<>();
		if (verbList == null) {
			return result;
		}
		
		for (Verb verb : verbList) {
			result.add(DTOMapper.getVerbDTO(verb));
		}
		return result;
	}

	public static VerbRSDTO getVerbDTO(Verb verb) {
		VerbRSDTO verbRSDTO = new VerbRSDTO();
		
		verbRSDTO.setBack(verb.getBack());
		verbRSDTO.setFront(verb.getFront());
		verbRSDTO.setId(verb.getId());
		verbRSDTO.setCreated(verb.getCreated());
		verbRSDTO.setFrontAudio(verb.getFrontAudio());
		verbRSDTO.setBackAudio(verb.getBackAudio());
		verbRSDTO.setFrontTranscription(verb.getFrontTranscription());
		verbRSDTO.setBackTranscription(verb.getBackTranscription());
		
		return verbRSDTO;
	}

	public static List<Verb> getVerbList(List<VerbRSDTO> verbList) {
		List<Verb> result = new ArrayList<>();
		if (verbList != null) {
			for (VerbRSDTO verbRSDTO : verbList) {
				result.add(DTOMapper.getVerb(verbRSDTO));
			}
		}
		
		return result;
	}

	public static Verb getVerb(VerbRSDTO verbRSDTO) {
		Verb result = new Verb();
		result.setFront(verbRSDTO.getFront());
		result.setFrontTranscription(verbRSDTO.getFrontTranscription());
		result.setFrontAudio(verbRSDTO.getFrontAudio());
		result.setBack(verbRSDTO.getBack());
		result.setBackTranscription(verbRSDTO.getBackTranscription());
		result.setBackAudio(verbRSDTO.getBackAudio());
		return result;
	}

	public static Box getBox(BoxRSDTO boxrsdto) {
		Box box = new Box();
		
		box.setBoxName(boxrsdto.getName());
		box.setFront(boxrsdto.getFront());
		box.setBack(boxrsdto.getBack());
		box.setPublic(boxrsdto.isPublic());
			
		box.setVerbList(DTOMapper.getVerbList(boxrsdto.getVerbList()));
		
		return box;
	}

	public static List<BoxRSDTO> getBoxDTOListOnly(List<Box> boxList) {
		List<BoxRSDTO> result = new ArrayList<>();
		
		for (Box box : boxList) {
			result.add(DTOMapper.getBoxDTOOnly(box));
		}
		
		return result;
	}

	public static BoxPlayRSDTO getBoxPlayDTO(List<Play> playList, Box box) {
		BoxPlayRSDTO result = new BoxPlayRSDTO();
		
		for (Play p : playList) {
			VerbPlayRSDTO e = getVerbPlayDTO(p);
			result.getVerbPlayList().add(e);
		}
		
		result.setProgressFront(box.getProgressFront());
		result.setProgressBack(box.getProgressBack());
		result.setBox(getBoxDTOOnly(box));
		
		return result;
	}
	
	private static VerbPlayRSDTO getVerbPlayDTO(Play p) {
		VerbPlayRSDTO result = new VerbPlayRSDTO();
		result.setId(p.getId());
		result.setLastModified(p.getLastModified());
		result.setProgressBack(p.getCorrectBacks() / Play.MAX_CORRECTNESS_DEGREE * 100.0);
		result.setProgressFront(p.getCorrectFronts() / Play.MAX_CORRECTNESS_DEGREE * 100.0);
		result.setCorrectFronts(p.getCorrectFronts());
		result.setCorrectBacks(p.getCorrectBacks());
		result.setVerb(getVerbDTO(p.getVerb()));
		
		return result;
	}

	
}
