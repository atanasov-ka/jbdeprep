package org.vb.backend.jpa.pojos;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "User")
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	@Column(name = "username")
	private String username;
	
	@NotNull
	@Column(name = "role")
	private String role;
	
	@NotNull
	@Column(name = "date_time_created")
	private Date created;
	
	// bidirectional mapping. Both entities "are knowing about the relation"
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Box> boxList;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Box> getBoxList() {
		return boxList;
	}
	public void setBoxList(List<Box> boxList) {
		this.boxList = boxList;
	}
	
	
}
