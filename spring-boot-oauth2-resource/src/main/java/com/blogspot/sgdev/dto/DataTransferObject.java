package com.blogspot.sgdev.dto;

import java.io.Serializable;


/**
 * Implemetations of the interface are canonical data strucstures that are used to integrate with external consumers (or producers, I guess). 
 * We should not leak underlying data field names into these classes (unless they by chance happen to be the same).
 *  
 * @author Paul Gilowey
 *
 * @param <DO> Domain Object
 */

public interface DataTransferObject<DO> extends Serializable {

	/**
	 * Subclasses should implement this method to call their specific ModelAdapter.getInstance().toDO(this) implementations.
	 * 
	 * @return A subclass of DoimainObject
	 */
	public DO toDO();
	
}