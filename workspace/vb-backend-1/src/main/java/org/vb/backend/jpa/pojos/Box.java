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
	@Column(name = "owner")
	private String owner;
	
	@NotNull
	@Column(name = "is_public")
	private boolean isPublic;
	
	@NotNull
	@Column(name = "date_time_created")
	private Date created;
	
	// unidirectional mapping. Only this entity "knows about the relation"
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_box_id")
	private List<Verb> verbList;

	// bidirectional mapping. Both entities "are knowing about the relation"
	@OneToMany(
			mappedBy = "box",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private List<Play> playList;

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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
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

	public List<Play> getPlayList() {
		return playList;
	}

	public void setPlayList(List<Play> playList) {
		this.playList = playList;
	}
}
