package org.vb.backend.jpa.pojos;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Verb")
@Table(name = "verb")
public class Verb {
	
	public Verb() {
		this.created = new Date();
	}
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "front")
	private String front;
	
	@Column(name = "back")
	private String back;
	
	@Column(name = "date_time_created")
	private Date created;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	
}
