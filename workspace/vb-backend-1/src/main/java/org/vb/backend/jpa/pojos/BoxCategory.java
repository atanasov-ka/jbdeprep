package org.vb.backend.jpa.pojos;

import java.util.List;

import javax.persistence.*;

@NamedQueries(value = {
		@NamedQuery(
				name = "findCategoryByIdAndUser",
				query = "select b from BoxCategory b where b.user.id = :userId and b.id = :categoryId")
})
@Entity(name = "BoxCategory")
@Table(name = "box_category")
public class BoxCategory {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "fk_user_id")
	private User user;

	@OneToMany(mappedBy = "boxCategory", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Box> boxList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Box> getBoxList() {
		return boxList;
	}

	public void setBoxList(List<Box> boxList) {
		this.boxList = boxList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
