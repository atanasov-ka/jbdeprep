package org.vb.backend.dto;

import java.util.ArrayList;
import java.util.List;

public class BoxPlayRSDTO {
	
	public BoxPlayRSDTO() {
		verbPlayList = new ArrayList<>();
		overralProgress = 0.;
	}
	
	private BoxRSDTO box;
	private Double overralProgress; // Percentage [0 - 100]
	private List<VerbPlayRSDTO> verbPlayList;
	
	public BoxRSDTO getBox() {
		return box;
	}
	public void setBox(BoxRSDTO box) {
		this.box = box;
	}
	public Double getOverralProgress() {
		return overralProgress;
	}
	public void setOverralProgress(Double overralProgress) {
		this.overralProgress = overralProgress;
	}
	public List<VerbPlayRSDTO> getVerbPlayList() {
		return verbPlayList;
	}
	public void setVerbPlayList(List<VerbPlayRSDTO> verbPlayList) {
		this.verbPlayList = verbPlayList;
	}
	
}
