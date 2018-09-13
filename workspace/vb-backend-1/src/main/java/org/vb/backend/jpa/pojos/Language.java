package org.vb.backend.jpa.pojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
 
@Entity(name = "Language")
@Table(name = "language", uniqueConstraints = @UniqueConstraint(columnNames = {"abbreviation"}))
@NamedQueries(value = {
		@NamedQuery(name="findLanguageByAbbreviation", query="select l from Language l where l.abbreviation = :abbr")
})
public class Language {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(min = 2, max =2)
	@Column(name = "abbreviation")
	private String abbreviation;

	public Language() {
		
	}
	
	public Language(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
}
