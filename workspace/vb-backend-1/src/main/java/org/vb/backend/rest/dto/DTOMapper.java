package org.vb.backend.rest.dto;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.vb.backend.jpa.pojos.Box;
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
		BoxRSDTO boxRSDTO = new BoxRSDTO();
		
		boxRSDTO.setId(box.getId());
		boxRSDTO.setName(box.getBoxName());
		boxRSDTO.setBack(box.getBoxName());
		boxRSDTO.setFront(box.getFront());
		boxRSDTO.setOwner(box.getOwner());
		boxRSDTO.setPublic(box.isPublic());
		boxRSDTO.setCreated(box.getCreated());
		
		if (box.getVerbList() != null) {
			boxRSDTO.setVerbCount(box.getVerbList().size());
			if (!box.getVerbList().isEmpty()) {
				boxRSDTO.setVerbList(DTOMapper.getVerbDTOList(box.getVerbList()));
			}
		}
		
		return boxRSDTO;
	}

	public static List<VerbRSDTO> getVerbDTOList(List<Verb> verbList) {
		List<VerbRSDTO> result = new LinkedList<>();
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
		
		return verbRSDTO;
	}

	public static List<Verb> getVerbList(List<VerbRSDTO> verbList) {
		List<Verb> result = new ArrayList<>();
		
		for (VerbRSDTO verbRSDTO : verbList) {
			result.add(DTOMapper.getVerb(verbRSDTO));
		}
		
		return result;
	}

	public static Verb getVerb(VerbRSDTO verbRSDTO) {
		Verb result = new Verb();
		result.setFront(verbRSDTO.getFront());
		result.setBack(verbRSDTO.getBack());
		return result;
	}

}
