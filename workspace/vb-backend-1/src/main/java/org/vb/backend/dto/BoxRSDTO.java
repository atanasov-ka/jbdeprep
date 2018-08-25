package org.vb.backend.dto;

import java.util.Date;
import java.util.List;

public class BoxRSDTO {
	private Long id;
	//@NotNull
	private String name;

	private String front;
	private String back;
	private boolean isPublic;
	private String owner;
	private List<VerbRSDTO> verbList;
	private Integer verbCount;
	private Date created;
	private Double progressFront;
	private Double progressBack;
	private Double levelBackHigh;
	private Double levelBackMid; 
	private Double levelBackLow;
	private Double levelFrontHigh;
	private Double levelFrontMid; 
	private Double levelFrontLow;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
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
	public boolean isPublic() {
		return isPublic;
	}
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public List<VerbRSDTO> getVerbList() {
		return verbList;
	}
	public void setVerbList(List<VerbRSDTO> verbList) {
		this.verbList = verbList;
	}
	public Integer getVerbCount() {
		return verbCount;
	}
	public void setVerbCount(Integer i) {
		this.verbCount = i;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
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
	public Double getLevelBackHigh() {
		return levelBackHigh;
	}
	public void setLevelBackHigh(Double levelBackHigh) {
		this.levelBackHigh = levelBackHigh;
	}
	public Double getLevelBackMid() {
		return levelBackMid;
	}
	public void setLevelBackMid(Double levelBackMid) {
		this.levelBackMid = levelBackMid;
	}
	public Double getLevelBackLow() {
		return levelBackLow;
	}
	public void setLevelBackLow(Double levelBackLow) {
		this.levelBackLow = levelBackLow;
	}
	public Double getLevelFrontHigh() {
		return levelFrontHigh;
	}
	public void setLevelFrontHigh(Double levelFrontHigh) {
		this.levelFrontHigh = levelFrontHigh;
	}
	public Double getLevelFrontMid() {
		return levelFrontMid;
	}
	public void setLevelFrontMid(Double levelFrontMid) {
		this.levelFrontMid = levelFrontMid;
	}
	public Double getLevelFrontLow() {
		return levelFrontLow;
	}
	public void setLevelFrontLow(Double levelFrontLow) {
		this.levelFrontLow = levelFrontLow;
	}
}
