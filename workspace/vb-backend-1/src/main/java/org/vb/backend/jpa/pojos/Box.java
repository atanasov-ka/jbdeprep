package org.vb.backend.jpa.pojos;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "Box")
@Table(name = "box")
public class Box {
	public Box() {
		this.created = new Date();
		this.isPublic = false;
		this.levelBackHigh = 0.;
		this.levelBackMid = 0.;
		this.levelBackLow = 0.;
		this.levelFrontHigh = 0.;
		this.levelFrontMid = 0.;
		this.levelFrontLow = 0.;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name =  "box_name")
	private String boxName;
	
	@ManyToOne
	@JoinColumn(name = "fk_front_lang")
	private Language front;
	
	@ManyToOne
	@JoinColumn(name = "fk_back_lang")
	private Language back;
		
	@NotNull
	@Column(name = "is_public")
	private boolean isPublic;
	
	@Column(name = "progress_front")
	private Double progressFront;
	
	@Column(name = "progress_back")
	private Double progressBack;
	
	@Column(name = "level_back_high")
	private Double levelBackHigh;
	
	@Column(name = "level_back_mid")
	private Double levelBackMid; 
	
	@Column(name = "level_back_low")
	private Double levelBackLow;
	
	@Column(name = "level_front_high")
	private Double levelFrontHigh;
	
	@Column(name = "level_front_mid")
	private Double levelFrontMid; 
	
	@Column(name = "level_front_low")
	private Double levelFrontLow;
	
	@Column(name = "last_play_date")
	private Date lastPlayDate;
	
	@NotNull
	@Column(name = "date_time_created")
	private Date created;
	
	@ManyToOne
	@JoinColumn(name = "fk_user")
	private User user;
	
	@OneToMany(mappedBy = "box", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Verb> verbList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBoxName() {
		return boxName;
	}

	public void setBoxName(String boxName) {
		this.boxName = boxName;
	}

	public Language getFront() {
		return front;
	}

	public void setFront(Language front) {
		this.front = front;
	}

	public Language getBack() {
		return back;
	}

	public void setBack(Language back) {
		this.back = back;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public List<Verb> getVerbList() {
		return verbList;
	}

	public void setVerbList(List<Verb> verbList) {
		this.verbList = verbList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public Date getLastPlayDate() {
		return lastPlayDate;
	}

	public void setLastPlayDate(Date lastPlayDate) {
		this.lastPlayDate = lastPlayDate;
	}
}
