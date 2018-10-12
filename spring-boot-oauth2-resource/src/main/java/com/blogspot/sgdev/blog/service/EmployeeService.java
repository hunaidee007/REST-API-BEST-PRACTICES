package com.blogspot.sgdev.blog.service;

import com.blogspot.sgdev.domain.DomainObject;
import com.blogspot.sgdev.dto.DataTransferObject;
import com.blogspot.sgdev.jpa.EmployeeRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface EmployeeService <DTO extends DataTransferObject<DO>, DO extends DomainObject<DTO>> extends GenericService<DTO, DO>{
	public Page<DTO> findByFirstName(String firstName, Pageable pageable);
	public Page<DTO> findByDepartmentId(Long departmentId, Pageable pageable);
	public String speak();
	public String hush();
	public void setEmployeeMessageService(MessageService messageService);
	public void setEmployeeRepository(EmployeeRepository employeeRepository);
}
