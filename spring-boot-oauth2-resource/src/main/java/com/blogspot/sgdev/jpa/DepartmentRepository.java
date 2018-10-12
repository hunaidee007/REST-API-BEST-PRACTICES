package com.blogspot.sgdev.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.blogspot.sgdev.domain.DepartmentDO;

@Repository
public interface DepartmentRepository extends PagingAndSortingRepository<DepartmentDO, Long>{
	
	
}
