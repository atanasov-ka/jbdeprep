package org.vb.backend.jpa.pojos;

import java.util.Date;

import javax.persistence.*;

@Entity(name = "Play")
@Table(name = "play")
public class Play {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "date_time_created")
	private Date created;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "box_id")
	private Box box;

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

	public Box getBox() {
		return box;
	}

	public void setBox(Box box) {
		this.box = box;
	}
}
