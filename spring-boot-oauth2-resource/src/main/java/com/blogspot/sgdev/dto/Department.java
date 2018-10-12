package com.blogspot.sgdev.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.blogspot.sgdev.adapter.DepartmentAdapter;
import com.blogspot.sgdev.domain.DepartmentDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "name", "known_name" })
@Relation(collectionRelation = "departments")
public class Department  extends  ResourceSupport implements Serializable, DataTransferObject<com.blogspot.sgdev.domain.DepartmentDO>  {

	private final static long serialVersionUID = -3304587465508285953L;
	
	@JsonProperty("id")
	@Null
	private Long id;
	
	@NotBlank(message="{NotBlank.department.name}")
	@JsonProperty("name")
	private String name;

	@JsonProperty("known_name")
	@NotBlank(message="{NotBlank.knownName.name}")
	private String knownName;

	@JsonProperty("modified")
	private Date modified;
	
	
	@JsonProperty("id")
	public Long getIds() {
		return id;
	}


	@JsonProperty("id")
	public void setId(Long id) {
		this.id = id;
	}


	@JsonProperty("name")
	public String getName() {
		return name;
	}


	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("known_name")
	public String getKnownName() {
		return knownName;
	}

	@JsonProperty("known_name")
	public void setKnownName(String knownName) {
		this.knownName = knownName;
	}

	@JsonProperty("modified")
	public Date getModified() {
		return modified;
	}

	@JsonProperty("modified")
	public void setModified(Date modified) {
		this.modified = modified;
	}


	@Override
	public DepartmentDO toDO() {
		return DepartmentAdapter.getInstance().toDO(this);
	}

}
