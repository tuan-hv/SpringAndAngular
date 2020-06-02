package net.guides.springboot2.springboot2jpacrudexample.convertDTO;

import net.guides.springboot2.springboot2jpacrudexample.dto.EmployeeDTO;
import net.guides.springboot2.springboot2jpacrudexample.entity.Employee;

public class EmployeeCovert {
    public static EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();

        employeeDTO.setId(employee.getId());
        employeeDTO.setFirstName(employee.getFirstName());
        employeeDTO.setLastName(employee.getLastName());
        employeeDTO.setAddress(employee.getAddress());
        employeeDTO.setPhoneNumber(employee.getPhoneNumber());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setStatus(employee.getStatus());

        return employeeDTO;
    }

    public static Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO){
        Employee employee = new Employee();

        employee.setId(employeeDTO.getId());
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setAddress(employeeDTO.getAddress());
        employee.setPhoneNumber(employeeDTO.getPhoneNumber());
        employee.setEmail(employeeDTO.getEmail());
        employee.setStatus(employeeDTO.getStatus());

        return employee;
    }
}
