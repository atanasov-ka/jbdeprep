package org.vb.backend.jpa.pojos;

import java.util.Date;

import javax.persistence.*;

@Entity(name = "Play")
@Table(name = "play")
public class Play {
	
	// This can be a setting of the Box Play
	public static final Long MAX_CORRECTNESS_DEGREE = 3L;
	public static final Long MIN_CORRECTNESS_DEGREE = 0L;
	
	public Play() {
		correctBacks = MIN_CORRECTNESS_DEGREE;
		correctFronts = MIN_CORRECTNESS_DEGREE;
		lastModified = new Date();
	}
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "date_time_created")
	private Date lastModified;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "verb_id")
	private Verb verb;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(name = "correct_backs")
	private Long correctBacks;
	
	@Column(name = "correct_fronts")
	private Long correctFronts;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date date) {
		this.lastModified = date;
	}

	public Verb getVerb() {
		return verb;
	}

	public void setVerb(Verb verb) {
		this.verb = verb;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User owner) {
		this.user = owner;
	}

	public Long getCorrectBacks() {
		return correctBacks;
	}

	public void addCorrectBack() {
		if (correctBacks < MAX_CORRECTNESS_DEGREE) {
			correctBacks++;
		}
	}
	
	public void addWrongBack() {
		if (correctBacks > MIN_CORRECTNESS_DEGREE) {
			correctBacks--;
		}
	}

	public Long getCorrectFronts() {
		return correctFronts;
	}

	public void setCorrectFront() {
		if (correctFronts < MAX_CORRECTNESS_DEGREE) {
			correctFronts++;
		}
	}
	
	public void addWrongFront() {
		if (correctFronts > MIN_CORRECTNESS_DEGREE) {
			correctFronts--;
		}
	}
}
