package com.blogspot.sgdev.adapter;

/**
 * Defines methods required for transforming between DOs and DTOs.
 * 
 * @author Paul Gilowey
 *
 * @param <DTO> Data Transfer Object
 * @param <DO> Domain Object
 */

public interface ModelAdapter<DTO, DO> {
	public DTO toDTO(DO theDo);
	public DO toDO(DTO dto);
}