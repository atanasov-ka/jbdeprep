package org.vb.backend.dto;

import java.util.ArrayList;
import java.util.List;

public class BoxPlayRSDTO {
	
	public BoxPlayRSDTO() {
		verbPlayList = new ArrayList<>();
		//progressFront = 0.;
		//progressBack = 0.;
	}
	
	private BoxRSDTO box;
	//private Double progressFront; // Percentage [0 - 100]
	//private Double progressBack; // Percentage [0 - 100]
	private List<VerbPlayRSDTO> verbPlayList;
	
	public BoxRSDTO getBox() {
		return box;
	}
	public void setBox(BoxRSDTO box) {
		this.box = box;
	}
//	public Double getProgressFront() {
//		return progressFront;
//	}
//	public void setProgressFront(Double progressFront) {
//		this.progressFront = progressFront;
//	}
//	public Double getProgressBack() {
//		return progressBack;
//	}
//	public void setProgressBack(Double progressBack) {
//		this.progressBack = progressBack;
//	}
	public List<VerbPlayRSDTO> getVerbPlayList() {
		return verbPlayList;
	}
	public void setVerbPlayList(List<VerbPlayRSDTO> verbPlayList) {
		this.verbPlayList = verbPlayList;
	}
	
}
