package com.blogspot.sgdev.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;

import com.blogspot.sgdev.blog.controllers.DepartmentController;
import com.blogspot.sgdev.blog.controllers.EmployeeController;
import com.blogspot.sgdev.dto.Department;

public class DepartmentResourceAssembler extends ResourceAssemblerSupport<Department,Department> {

	public DepartmentResourceAssembler() {
	    super(DepartmentController.class, Department.class);
	  }

	@Override
	public Department toResource(Department department) {
		
		try {
			System.out.println(" -- in DepartmentResourceAssembler -- adding links");
			department.add(linkTo(methodOn(DepartmentController.class).get(department.getIds()))
					.withSelfRel()
					.withType(RequestMethod.GET.name())
					.withMedia(MediaType.APPLICATION_JSON_VALUE));
			department.add(linkTo(methodOn(EmployeeController.class).find(department.getIds(), null, null))
					.withRel("employees")
					.withType(RequestMethod.GET.name())
					.withMedia(MediaType.APPLICATION_JSON_VALUE));
			department.add(linkTo(methodOn(DepartmentController.class).delete(department.getIds()))
					.withRel("delete")
					.withType(RequestMethod.DELETE.name())
					.withMedia(MediaType.APPLICATION_JSON_VALUE));
			department.add(linkTo(methodOn(DepartmentController.class).update(department.getIds(), department))
					.withRel("update")
					.withType(RequestMethod.PUT.name())
					.withMedia(MediaType.APPLICATION_JSON_VALUE));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     return department;
	}
}
