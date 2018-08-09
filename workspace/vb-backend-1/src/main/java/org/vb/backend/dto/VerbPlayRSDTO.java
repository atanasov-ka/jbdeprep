package org.vb.backend.dto;

import java.util.Date;

public class VerbPlayRSDTO {
	private Long id;
	private Double progressFront; // Percentage [0 - 100]
	private Double progressBack; // Percentage [0 - 100]
	private Date lastModified;
	private VerbRSDTO verb;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getProgressFront() {
		return progressFront;
	}
	public void setProgressFront(Double progressFront) {
		this.progressFront = progressFront;
	}
	public Double getProgressBack() {
		return progressBack;
	}
	public void setProgressBack(Double progressBack) {
		this.progressBack = progressBack;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	public VerbRSDTO getVerb() {
		return verb;
	}
	public void setVerb(VerbRSDTO verb) {
		this.verb = verb;
	}
}
