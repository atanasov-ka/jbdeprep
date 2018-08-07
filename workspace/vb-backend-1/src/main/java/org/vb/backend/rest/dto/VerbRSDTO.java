package org.vb.backend.rest.dto;

import java.util.Date;

public class VerbRSDTO {
	private Long id;
	
	private String front;
	private String frontTranscription;
	private String frontAudio;
	
	private String back;
	private String backTranscription;
	private String backAudio;
	
	private Date created;
	
	public String getFront() {
		return front;
	}
	public void setFront(String front) {
		this.front = front;
	}
	public String getBack() {
		return back;
	}
	public void setBack(String back) {
		this.back = back;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getFrontTranscription() {
		return frontTranscription;
	}
	public void setFrontTranscription(String frontTranscription) {
		this.frontTranscription = frontTranscription;
	}
	public String getFrontAudio() {
		return frontAudio;
	}
	public void setFrontAudio(String frontAudio) {
		this.frontAudio = frontAudio;
	}
	public String getBackTranscription() {
		return backTranscription;
	}
	public void setBackTranscription(String backTranscription) {
		this.backTranscription = backTranscription;
	}
	public String getBackAudio() {
		return backAudio;
	}
	public void setBackAudio(String backAudio) {
		this.backAudio = backAudio;
	}	
}
