package com.blogspot.sgdev.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.blogspot.sgdev.domain.DomainObject;
import com.blogspot.sgdev.dto.DataTransferObject;
import com.blogspot.sgdev.jpa.DepartmentRepository;

public interface DepartmentService  <DTO extends DataTransferObject<DO>, DO extends DomainObject<DTO>> extends GenericService<DTO, DO>{
	public Page<DTO> findByFirstName(String firstName, Pageable pageable);
	public String speak();
	public String hush();
	public void setDepartmentMessageService(MessageService messageService);
	public void setDepartmentRepository(DepartmentRepository departmentRepository);
}