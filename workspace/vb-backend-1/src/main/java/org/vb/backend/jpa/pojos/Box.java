package org.vb.backend.jpa.pojos;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "Box")
@Table(name = "box")
public class Box {
	public Box() {
		this.created = new Date();
		this.isPublic = false;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Size(min = 2, max = 150)
	@Column(name =  "box_name")
	private String boxName;
	
	@Size(min = 2, max = 20)
	@Column(name = "front")
	private String front;
	
	@Size(min = 2, max = 20)
	@Column(name = "back")
	private String back;
		
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
	
	@NotNull
	@Column(name = "date_time_created")
	private Date created;
	
	// child side. bi-directional mapping. Both entities "are knowing about the relation"
	@ManyToOne
	@JoinColumn(name = "fk_user")
	private User user;
	
// TODO parent side. bi-directional mapping. Only this entity "knows about the relation"
//	@OneToMany(mappedBy = "box", cascade = CascadeType.ALL, orphanRemoval = true)

	// unidirectional mapping. Only this entity "knows about the relation"
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_box_id")
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
}
