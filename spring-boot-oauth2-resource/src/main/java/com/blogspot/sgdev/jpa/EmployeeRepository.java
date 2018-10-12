package com.blogspot.sgdev.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.blogspot.sgdev.domain.DepartmentDO;
import com.blogspot.sgdev.domain.EmployeeDO;

/**
* This JPA repository is used to interact with the data store.
*  
* Find a Spring Data example using a CRUD JPA Repository here: https://spring.io/guides/gs/accessing-data-jpa/
* 
* @author Paul Gilowey
* 
**/

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<EmployeeDO, Long>{
	
	public Page<EmployeeDO> findByNameOrderBySurnameAsc(String name, Pageable pageable);
	public Page<EmployeeDO> findByDepartmentDO(DepartmentDO departmentDO, Pageable pageable);
	
}
