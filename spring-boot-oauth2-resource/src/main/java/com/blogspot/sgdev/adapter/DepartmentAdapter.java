package com.blogspot.sgdev.adapter;

import com.blogspot.sgdev.domain.DepartmentDO;
import com.blogspot.sgdev.dto.Department;

public class DepartmentAdapter implements ModelAdapter<Department, DepartmentDO>  {


private static DepartmentAdapter departmentAdapter;
	
    static{
        try{
        	departmentAdapter = new DepartmentAdapter();
        }catch(Exception e){
            throw new RuntimeException("Exception occured in creating DepartmentAdapter.");
        }
    }
    
    public static DepartmentAdapter getInstance(){
        return departmentAdapter;
    }
	
	private DepartmentAdapter() {}
	    
	@Override
	public Department toDTO(DepartmentDO departmentDO) {
		
		if (departmentDO == null) return null;
		
		Department department = new Department();
		department.setId(departmentDO.getId());
		department.setName(departmentDO.getName());
		department.setKnownName(departmentDO.getKnownName());
		department.setModified(departmentDO.getModified());
		return department;
	}
	
	@Override
	public DepartmentDO toDO(Department department) {
		
		if (department == null) return null;
		
		DepartmentDO departmentDO = new DepartmentDO();
		departmentDO.setId(department.getIds());
		departmentDO.setName(department.getName());
		departmentDO.setKnownName(department.getKnownName());
		departmentDO.setModified(department.getModified());
		return departmentDO;
	}

}
