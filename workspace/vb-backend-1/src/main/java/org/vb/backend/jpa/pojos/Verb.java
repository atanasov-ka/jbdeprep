package org.vb.backend.jpa.pojos;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity(name = "Verb")
@Table(name = "verb")
public class Verb {
	
	public Verb() {
		this.created = new Date();
	}
	
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	@Column(name = "front")
	private String front;
	
	@NotNull
	@Column(name = "front_transcription")
	private String frontTranscription;
	
	@NotNull
	@Column(name = "front_audio")
	private String frontAudio;
	
	@NotNull
	@Column(name = "back")
	private String back;
	
	@NotNull
	@Column(name = "back_transcription")
	private String backTranscription;
	
	@NotNull
	@Column(name = "back_audio")
	private String backAudio;
	
	@NotNull
	@Column(name = "date_time_created")
	private Date created;
	
	@ManyToOne
	@JoinColumn(name = "fk_box_id")
	private Box box;

	public Box getBox() {
		return box;
	}
	
	public void setBox(Box box) {
		this.box = box;
	}

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
