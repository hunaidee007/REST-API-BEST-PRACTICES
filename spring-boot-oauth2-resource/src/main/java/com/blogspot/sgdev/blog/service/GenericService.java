package com.blogspot.sgdev.blog.service;

import com.blogspot.sgdev.domain.DomainObject;
import com.blogspot.sgdev.dto.DataTransferObject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GenericService<DTO extends DataTransferObject<DO>, DO extends DomainObject<DTO>> {

	public DTO create(DTO dto) throws Exception;

	public DTO update(DTO dto) throws Exception;

	public void delete(Long id) throws Exception;

	public DTO findById(Long id) throws Exception;

	public Page<DTO> findAll(Pageable pageable);

}
