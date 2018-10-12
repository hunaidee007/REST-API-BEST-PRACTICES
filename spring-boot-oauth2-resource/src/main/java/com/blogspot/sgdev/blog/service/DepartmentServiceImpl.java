package com.blogspot.sgdev.blog.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blogspot.sgdev.adapter.DepartmentAdapter;
import com.blogspot.sgdev.blog.controllers.ApiSubError;
import com.blogspot.sgdev.domain.DepartmentDO;
import com.blogspot.sgdev.domain.EmployeeDO;
import com.blogspot.sgdev.dto.Department;
import com.blogspot.sgdev.dto.Employee;
import com.blogspot.sgdev.exceptions.NotFoundException;
import com.blogspot.sgdev.exceptions.ValidationException;
import com.blogspot.sgdev.jpa.DepartmentRepository;
import com.blogspot.sgdev.jpa.EmployeeRepository;

@Service
public class DepartmentServiceImpl extends AbstractService<Department, DepartmentDO>
implements DepartmentService<Department, DepartmentDO> {
	
	@Value("${myapp.myvariable}")
	private String myVariable;

	private DepartmentRepository departmentRepository;
	private DepartmentAdapter departmentAdapter = DepartmentAdapter.getInstance();
	private MessageService messageService;

	public DepartmentServiceImpl() {
		super();
	}

	@Autowired
	public DepartmentServiceImpl(DepartmentRepository departmentRepository, MessageService messageService) {
		super();
		this.departmentRepository = departmentRepository;
		this.messageService = messageService;
	}

	@Override
	public Department create(Department department) throws Exception {
		System.out.println(" -- in DepartmentServiceImpl");

		DepartmentDO employeeDO = departmentRepository.save(department.toDO());

		return departmentAdapter.toDTO(employeeDO);
	}

	@Override
	public Department update(Department department) throws Exception {
		DepartmentDO departmentDO = departmentRepository.save(department.toDO());

		return departmentAdapter.toDTO(departmentDO);
	}

	@Override
	public void delete(Long id) throws Exception {
		if (id==null)
			throw new ValidationException(
					messageService.format(MessageServiceImpl.ResponseMessage.BAD_REQUEST_NO_ID_PROVIDED));

		try {
			departmentRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new NotFoundException(messageService.format(MessageServiceImpl.ResponseMessage.NOT_FOUND));
		}
		
	}

	@Override
	public Department findById(Long id) throws Exception {
		Optional<DepartmentDO> departmentDO = departmentRepository.findById(id);
		if (!departmentDO.isPresent()) {
			throw new NotFoundException(
					messageService.format(MessageServiceImpl.ResponseMessage.NOT_FOUND, "remove me", "later..."));
		}
		return departmentAdapter.toDTO(departmentDO.get());
	}

	@Override
	public Page<Department> findAll(Pageable pageable) {
		Page<DepartmentDO> pageOfDOs = departmentRepository.findAll(pageable);

		Page<Department> pageOfDTOs = mapToDTOs(pageable, pageOfDOs);

		return pageOfDTOs;
	}

	@Override
	public Page<Department> findByFirstName(String firstName, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String speak() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String hush() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDepartmentMessageService(MessageService messageService) {
		this.messageService = messageService;
		
	}

	@Override
	public void setDepartmentRepository(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
		
	}



}
