package com.blogspot.sgdev.adapter;

import com.blogspot.sgdev.domain.EmployeeDO;
import com.blogspot.sgdev.dto.Employee;

public class EmployeeAdapter implements ModelAdapter<Employee, EmployeeDO> {
	
	private static EmployeeAdapter employeeAdapter;
	
    static{
        try{
        	employeeAdapter = new EmployeeAdapter();
        }catch(Exception e){
            throw new RuntimeException("Exception occured in creating EmployeeAdapter.");
        }
    }
    
    public static EmployeeAdapter getInstance(){
        return employeeAdapter;
    }
	
	private EmployeeAdapter() {}
	    
	@Override
	public Employee toDTO(EmployeeDO employeeDO) {
		
		if (employeeDO == null) return null;
		
		Employee employee = new Employee();
		employee.setId(employeeDO.getId());
		employee.setFirstName(employeeDO.getName());
		employee.setLastName(employeeDO.getSurname());
		employee.setKnownName(employeeDO.getKnownName());
		employee.setModified(employeeDO.getModified());
		return employee;
	}
	
	@Override
	public EmployeeDO toDO(Employee employee) {
		
		if (employee == null) return null;
		
		EmployeeDO employeeDO = new EmployeeDO();
		//employeeDO.setId(employee.getId());
		employeeDO.setName(employee.getFirstName());
		employeeDO.setSurname(employee.getLastName());
		employeeDO.setKnownName(employee.getKnownName());
		employeeDO.setModified(employee.getModified());
		employeeDO.setDepartmentDO(employee.getDepartment().toDO());
		return employeeDO;
	}
	
}
