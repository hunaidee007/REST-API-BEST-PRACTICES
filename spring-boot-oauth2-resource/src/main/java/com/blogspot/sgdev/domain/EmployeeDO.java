package com.blogspot.sgdev.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.blogspot.sgdev.adapter.EmployeeAdapter;
import com.blogspot.sgdev.dto.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "Employee")
public class EmployeeDO implements DomainObject<Employee> {

	private static final long serialVersionUID = -820283601197541315L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "VARCHAR(50) NOT NULL")
	private String name;

	@Column(columnDefinition = "VARCHAR(50) NOT NULL")
	private String surname;

	@Column(columnDefinition = "VARCHAR(50) NOT NULL")
	private String knownName;
	
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date modified;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private DepartmentDO departmentDO;

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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
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
	
	public DepartmentDO getDepartmentDO() {
		return departmentDO;
	}

	public void setDepartmentDO(DepartmentDO departmentDO) {
		this.departmentDO = departmentDO;
	}


	@Override
	public Employee toDTO() {
		return EmployeeAdapter.getInstance().toDTO(this);
	}

}
