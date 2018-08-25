package org.vb.backend.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BoxPlayRSDTO {
	
	public BoxPlayRSDTO() {
		verbPlayList = new ArrayList<>();
	}
	
	private BoxRSDTO box;
	private List<VerbPlayRSDTO> verbPlayList;
	private Date lastPlayDate;
	
	public BoxRSDTO getBox() {
		return box;
	}
	public void setBox(BoxRSDTO box) {
		this.box = box;
	}
	public List<VerbPlayRSDTO> getVerbPlayList() {
		return verbPlayList;
	}
	public void setVerbPlayList(List<VerbPlayRSDTO> verbPlayList) {
		this.verbPlayList = verbPlayList;
	}
	public Date getLastPlayDate() {
		return lastPlayDate;
	}
	public void setLastPlayDate(Date lastPlayDate) {
		this.lastPlayDate = lastPlayDate;
	}
}
