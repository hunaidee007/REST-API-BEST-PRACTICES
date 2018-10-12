package com.blogspot.sgdev.domain;

import java.io.Serializable;

/**
 * This is the common interface for all Domain Objects.
 * 
 * @author Paul Gilowey
 *
 * @param <DTO> Data Transfer Object
 */

public interface DomainObject<DTO> extends Serializable {

	/**
	 * Subclasses should implement this method to call their ModelAdapter.getInstance().toDTO(this) implementations.
	 * 
	 * @return A subclass of DataTransferObject
	 */
	public DTO toDTO();
	
}