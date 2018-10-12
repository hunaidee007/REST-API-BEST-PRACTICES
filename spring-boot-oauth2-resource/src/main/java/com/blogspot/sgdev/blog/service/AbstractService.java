package com.blogspot.sgdev.blog.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.blogspot.sgdev.domain.DomainObject;
import com.blogspot.sgdev.dto.DataTransferObject;

@Component
abstract public class AbstractService<DTO extends DataTransferObject<DO>, DO extends DomainObject<DTO>> implements GenericService<DTO, DO>{

	@Autowired
	Environment appEnvirionment;	
	
	@Autowired
	public AbstractService() {
		super();
	}

	public Page<DTO> mapToDTOs(Pageable pageable, Page<DO> pageOfDOs) {
		
		List<DTO> theDTOs = new ArrayList<DTO>();
		
		for (DO theDO : pageOfDOs.getContent()) {
			theDTOs.add( theDO.toDTO() );
		}

		Page<DTO> pageOfDTOs = new PageImpl<DTO>(theDTOs, pageable, pageOfDOs.getTotalElements());

		return pageOfDTOs;
	}	
	
}		