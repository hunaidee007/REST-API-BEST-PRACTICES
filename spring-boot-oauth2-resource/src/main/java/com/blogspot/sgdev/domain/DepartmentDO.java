package com.blogspot.sgdev.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.blogspot.sgdev.adapter.DepartmentAdapter;
import com.blogspot.sgdev.dto.Department;
/**
 * This is a Domain Object.
 * 
 * This object should bind directly to your data source. It should be a
 * represenation of your physical data structure. Notice how the surname is
 * called Surname here, but in the DTO it's called LastName.
 * 
 * Find a Spring Data example using JPA here:
 * https://spring.io/guides/gs/accessing-data-jpa/
 * 
 */

@Entity
@Table(name = "Department")
public class DepartmentDO implements DomainObject<Department>{

	private static final long serialVersionUID = -820283601197541315L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "VARCHAR(50) NOT NULL")
	private String name;


	@Column(columnDefinition = "VARCHAR(50) NOT NULL")
	private String knownName;
	
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date modified;

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



	public String getKnownName() {
		return knownName;
	}

	public void setKnownName(String knownName) {
		this.knownName = knownName;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	@Override
	public Department toDTO() {
		return DepartmentAdapter.getInstance().toDTO(this);
	}

}
