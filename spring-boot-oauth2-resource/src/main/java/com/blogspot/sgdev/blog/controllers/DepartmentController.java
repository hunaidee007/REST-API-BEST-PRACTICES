package com.blogspot.sgdev.blog.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import javax.validation.Valid;

import com.blogspot.sgdev.assembler.DepartmentResourceAssembler;
import com.blogspot.sgdev.authorisation.HasMaintainerAuthority;
import com.blogspot.sgdev.authorisation.HasViewerOrMaintainerAuthority;
import com.blogspot.sgdev.blog.controllers.EmployeeController;
import com.blogspot.sgdev.blog.service.DepartmentService;
import com.blogspot.sgdev.blog.service.MessageServiceImpl;
import com.blogspot.sgdev.domain.DepartmentDO;
import com.blogspot.sgdev.dto.Department;

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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/v1/departments")
public class DepartmentController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	private DepartmentService<Department, DepartmentDO> departmentService;
	private MessageServiceImpl messageservice;
	
	@Autowired
	public DepartmentController(DepartmentService<Department, DepartmentDO> departmentService, MessageServiceImpl messageService) {
		this.departmentService = departmentService;
		this.messageservice = messageService;
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/hal+json;charset=utf-8")
	@PreAuthorize("#oauth2.hasScope('add_department') and hasRole('ROLE_TRUSTED_CLIENT')")
	public HttpEntity<?> create(@Valid @RequestBody Department department) throws Exception {
		
		Department savedDepartment=departmentService.create(department);
		return new ResponseEntity<Object>(new DepartmentResourceAssembler().toResource(savedDepartment), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces  = "application/hal+json;charset=utf-8")
	@PreAuthorize("#oauth2.hasScope('view_department') and hasRole('ROLE_TRUSTED_CLIENT')")
	@ResponseBody
	public HttpEntity<?> get(@PathVariable(value = "id") Long id) throws Exception {
		Department department = departmentService.findById(id);
		return new ResponseEntity<Object>(new DepartmentResourceAssembler().toResource(department), null, (department == null) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/hal+json;charset=utf-8")
	@PreAuthorize("#oauth2.hasScope('view_all_departments') and hasRole('ROLE_TRUSTED_CLIENT')")
	@ResponseBody
	public HttpEntity<?> find(@RequestParam(value = "name", required = false) String firstName,
			Pageable pageable, PagedResourcesAssembler<Department> assembler) throws Exception {
		Page<Department> page = null;
		
		page = departmentService.findAll(pageable);
		
		for (Department d  : page.getContent()) {
		     System.out.println(d.hasLinks());
		}
		

		return new ResponseEntity<>(assembler.toResource(page,new DepartmentResourceAssembler()), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("#oauth2.hasScope('delete_department') and hasRole('ROLE_TRUSTED_CLIENT')")
	@ResponseBody
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws Exception {
		departmentService.delete(id);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("#oauth2.hasScope('add_department') and hasRole('ROLE_TRUSTED_CLIENT')")
	@ResponseBody
	public ResponseEntity<?> update(@PathVariable (value = "id") Long departmentId, @RequestBody Department department)
			throws Exception {
		return new ResponseEntity<Object>(new DepartmentResourceAssembler().toResource(departmentService.update(department)), HttpStatus.OK);
	}
}
