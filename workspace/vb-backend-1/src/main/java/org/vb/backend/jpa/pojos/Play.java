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
	@JoinColumn(name = "verb_id")
	private Verb verb;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User owner;

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

	public Verb getVerb() {
		return verb;
	}

	public void setVerb(Verb verb) {
		this.verb = verb;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
}
