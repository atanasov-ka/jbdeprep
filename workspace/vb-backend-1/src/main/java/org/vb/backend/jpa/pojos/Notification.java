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
import javax.validation.constraints.NotNull;

@Table(name = "notification")
@Entity(name = "Notification")
public class Notification {
	
	public Notification() {
		this.seen = false;
		this.created = new Date();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "fk_user_id")
	private User user;  
	
	@Column(name = "created")
	private Date created;
	
	@Column(name = "is_seen")
	@NotNull
	private boolean seen;
	
	@NotNull
	@Column(name = "message_short")
	private String messageShort;
	
	@NotNull
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
