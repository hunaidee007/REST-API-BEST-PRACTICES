package com.blogspot.sgdev.blog.service;

import com.blogspot.sgdev.adapter.EmployeeAdapter;
import com.blogspot.sgdev.domain.DepartmentDO;
import com.blogspot.sgdev.domain.EmployeeDO;
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

import com.blogspot.sgdev.dto.Employee;
import com.blogspot.sgdev.exceptions.NotFoundException;
import com.blogspot.sgdev.exceptions.ValidationException;
import com.blogspot.sgdev.jpa.EmployeeRepository;

@Service
public class EmployeeServiceImpl extends AbstractService<Employee, EmployeeDO>
		implements EmployeeService<Employee, EmployeeDO> {

	@Value("${myapp.myvariable}")
	private String myVariable;

	private EmployeeRepository employeeRepository;
	private EmployeeAdapter employeeAdapter = EmployeeAdapter.getInstance();
	private MessageService messageService;

	public EmployeeServiceImpl() {
		super();
	}

	@Autowired
	public EmployeeServiceImpl(EmployeeRepository employeeRepository, MessageService messageService) {
		super();
		this.employeeRepository = employeeRepository;
		this.messageService = messageService;
	}

	@Override
	public Employee create(Employee employee) {
		
		System.out.println(" -- in EmployeeServiceImpl");

		List<String> validationErrors = new ArrayList<String>();
		if (employee.getIds()!=null)
			validationErrors
					.add(messageService.format(MessageServiceImpl.ResponseMessage.BAD_REQUEST_ID_MUST_NOT_BE_PROVIDED));
		if (StringUtils.isNullOrEmpty(employee.getFirstName()))
			validationErrors.add(messageService
					.format(MessageServiceImpl.ResponseMessage.BAD_REQUEST_REQUIRED_FIELD_MISSING, "first_name"));
		if (StringUtils.isNullOrEmpty(employee.getKnownName()))
			validationErrors.add(messageService
					.format(MessageServiceImpl.ResponseMessage.BAD_REQUEST_REQUIRED_FIELD_MISSING, "known_name"));

		if (!validationErrors.isEmpty())
			throw new ValidationException(validationErrors, UUID.randomUUID().toString());

		EmployeeDO employeeDO = employeeRepository.save(employee.toDO());

		return employeeAdapter.toDTO(employeeDO);
	}

	@Override
	public Employee update(Employee employee) throws Exception {

		/*if (StringUtils.isNullOrEmpty(employee.getId()))
			throw new ValidationException(
					messageService.format(MessageServiceImpl.ResponseMessage.BAD_REQUEST_NO_ID_PROVIDED));

	*/	EmployeeDO employeeDO = employeeRepository.save(employee.toDO());

		return employeeAdapter.toDTO(employeeDO);
	}

	@Override
	public void delete(Long id) throws Exception {

		if (id==null)
			throw new ValidationException(
					messageService.format(MessageServiceImpl.ResponseMessage.BAD_REQUEST_NO_ID_PROVIDED));

		try {
			employeeRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new NotFoundException(messageService.format(MessageServiceImpl.ResponseMessage.NOT_FOUND));
		}
	}

	/**
	 * Find an Employee by their primary key id.
	 * 
	 * @return Returns NULL if not found.
	 */
	@Override
	public Employee findById(Long id) {

		Optional<EmployeeDO> employeeDO = employeeRepository.findById(id);
		if (!employeeDO.isPresent()) {
			throw new NotFoundException(
					messageService.format(MessageServiceImpl.ResponseMessage.NOT_FOUND, "remove me", "later..."));
		}
		return employeeAdapter.toDTO(employeeDO.get());

	}

	@Override
	public Page<Employee> findAll(Pageable pageable) {

		Page<EmployeeDO> pageOfDOs = employeeRepository.findAll(pageable);

		Page<Employee> pageOfDTOs = mapToDTOs(pageable, pageOfDOs);

		return pageOfDTOs;
	}

	@Override
	public Page<Employee> findByFirstName(String firstName, Pageable pageable) {

		Page<EmployeeDO> pageOfDOs = employeeRepository.findByNameOrderBySurnameAsc(firstName, pageable);

		Page<Employee> pageOfDTOs = mapToDTOs(pageable, pageOfDOs);

		return pageOfDTOs;
	}

	@Override
	public String speak() {
		return "That man is playing Galaga!";
	}

	@Override
	public String hush() {
		return "zzzZZZZzzzzzZZZ";
	}

	@Override
	public void setEmployeeRepository(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;

	}

	@Override
	public void setEmployeeMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	@Override
	public Page<Employee> findByDepartmentId(Long departmentId, Pageable pageable) {
		
		DepartmentDO departmentDO = new DepartmentDO();
		departmentDO.setId(departmentId);
		Page<EmployeeDO> pageOfDOs = employeeRepository.findByDepartmentDO(departmentDO, pageable);

		Page<Employee> pageOfDTOs = mapToDTOs(pageable, pageOfDOs);

		return pageOfDTOs;
	}
}
