package org.vb.backend.jpa.pojos;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "notification")
@Entity(name = "Notification")
public class Notification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;  
	
	@Column(name = "created")
	private Date created;
	
	@Column(name = "is_seen")
	private boolean seen;
	
	@Column(name = "message_title")
	private String messageTitle;
	
	@Column(name = "message_short")
	private String messageShort;
	
	@Column(name = "message_long")
	private String messageLong;

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

	public boolean isSeen() {
		return seen;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
	}

	public String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}

	public String getMessageShort() {
		return messageShort;
	}

	public void setMessageShort(String messageShort) {
		this.messageShort = messageShort;
	}

	public String getMessageLong() {
		return messageLong;
	}

	public void setMessageLong(String messageLong) {
		this.messageLong = messageLong;
	}
	
	
}
