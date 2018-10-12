package com.blogspot.sgdev.blog.controllers;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.blogspot.sgdev.authorisation.HasMaintainerAuthority;
import com.blogspot.sgdev.authorisation.HasViewerOrMaintainerAuthority;
import com.blogspot.sgdev.blog.service.DepartmentService;
import com.blogspot.sgdev.blog.service.EmployeeService;
import com.blogspot.sgdev.blog.service.MessageServiceImpl;
import com.blogspot.sgdev.blog.service.MessageServiceImpl.ResponseMessage;
import com.blogspot.sgdev.domain.DepartmentDO;
import com.blogspot.sgdev.domain.EmployeeDO;
import com.blogspot.sgdev.dto.Department;
import com.blogspot.sgdev.dto.Employee;
import com.blogspot.sgdev.exceptions.NotFoundException;
import com.blogspot.sgdev.exceptions.ValidationException;
import com.blogspot.sgdev.util.Utils;



/**
 * 
 * @author Paul Gilowey
 * 
 *         This is an example controller and the entry point for REST API calls.
 *         This application was built the links below as reference.
 *
 *         https://docs.spring.io/spring-data/data-commons/docs/1.6.1.RELEASE/reference/html/repositories.html
 *         https://spring.io/guides/gs/accessing-data-jpa/
 *         https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-part-seven-pagination/
 *         http://www.javainterviewpoint.com/spring-boot-crudrepository-example/
 *         https://medium.com/@nydiarra/secure-a-spring-boot-rest-api-with-json-web-token-reference-to-angular-integration-e57a25806c50
 * 
 *         SECURITY:
 *         https://docs.spring.io/spring-security/site/docs/current/reference/html/el-access.html
 * 
 */

@RestController
@RequestMapping("/rest/v1")
public class EmployeeController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	private EmployeeService<Employee, EmployeeDO> employeeService;
	private MessageServiceImpl messageservice;
	private DepartmentService<Department, DepartmentDO> departmentService;

	@Autowired
	public EmployeeController(DepartmentService<Department, DepartmentDO> departmentService, EmployeeService<Employee, EmployeeDO> employeeService, MessageServiceImpl messageService) {
		this.employeeService = employeeService;
		this.messageservice = messageService;
		this.departmentService= departmentService;
	}

	@RequestMapping(value = "/departments/{departmentId}/employees", method = RequestMethod.POST, produces = "application/json")
	@HasMaintainerAuthority
	public HttpEntity<?> create(@PathVariable (value = "departmentId") Long departmentId,@RequestBody Employee employee) throws Exception {
		Department department=  departmentService.findById(departmentId);
		if(department ==null) 
			throw new NotFoundException("No Department found for department id  : " + departmentId);
		employee.setDepartment(department);
		Employee savedEmployee=employeeService.create(employee);
		if(savedEmployee != null) {
			savedEmployee.add(linkTo(methodOn(EmployeeController.class).get(departmentId,savedEmployee.getIds())).withSelfRel());
		}
		return new ResponseEntity<Object>(savedEmployee, HttpStatus.CREATED);
	}


	/*@RequestMapping(value = "/departments/{departmentId}/employees/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@HasMaintainerAuthority
	@ResponseBody
	public ResponseEntity<?> update(@PathVariable (value = "departmentId") Long departmentId,@PathVariable(value = "id") String id, @RequestBody Employee employee)
			throws Exception {
		return new ResponseEntity<Object>(employeeService.update(employee), HttpStatus.OK);
	}*/

	/*@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@HasMaintainerAuthority
	@ResponseBody
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws Exception {
		employeeService.delete(id);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
*/
	@RequestMapping(value = "/departments/{departmentId}/employees/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@HasViewerOrMaintainerAuthority
	@ResponseBody
	public HttpEntity<?> get(@PathVariable (value = "departmentId") Long departmentId,@PathVariable(value = "id") Long id) throws Exception {
		Employee employee = employeeService.findById(id);
		if(employee != null) {
			employee.add(linkTo(methodOn(EmployeeController.class).get(departmentId,id)).withSelfRel());
		}
		return new ResponseEntity<Object>(employee, null, (employee == null) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}

	@RequestMapping(value = "/departments/{departmentId}/employees", method = RequestMethod.GET, produces = "application/json")
	@HasViewerOrMaintainerAuthority
	@ResponseBody
	public HttpEntity<?> find( @PathVariable (value = "departmentId") Long departmentId,
			Pageable pageable, PagedResourcesAssembler<Employee> assembler) throws Exception {
		Page<Employee> page = null;
		page = employeeService.findByDepartmentId(departmentId, pageable);
		
		return new ResponseEntity<>(assembler.toResource(page), HttpStatus.OK);
	}

}
