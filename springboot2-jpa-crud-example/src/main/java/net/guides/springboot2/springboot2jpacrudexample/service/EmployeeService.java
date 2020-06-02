package net.guides.springboot2.springboot2jpacrudexample.service;

import net.guides.springboot2.springboot2jpacrudexample.convertDTO.CompanyConvert;
import net.guides.springboot2.springboot2jpacrudexample.convertDTO.EmployeeCovert;
import net.guides.springboot2.springboot2jpacrudexample.dto.EmployeeDTO;
import net.guides.springboot2.springboot2jpacrudexample.entity.Company;
import net.guides.springboot2.springboot2jpacrudexample.entity.Employee;
import net.guides.springboot2.springboot2jpacrudexample.repository.CompanyRepository;
import net.guides.springboot2.springboot2jpacrudexample.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public List<EmployeeDTO> findAllEmployee() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        employees.forEach(e -> {
            EmployeeDTO employeeDTO = EmployeeCovert.convertEmployeeToEmployeeDTO(e);
            employeeDTO.setCompanyDTO(CompanyConvert.convertCompanyToCompanyDTO(e.getCompany()));
            employeeDTOS.add(employeeDTO);
        });
        return employeeDTOS;
    }

    public Optional<EmployeeDTO> getEmployeeById(Long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            return Optional.of(EmployeeCovert.convertEmployeeToEmployeeDTO(employee.get()));
        }
        return Optional.empty();
    }

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Optional<Company> company = companyRepository.findById(employeeDTO.getCompanyDTO().getId());
        if (company.isPresent()) {
            Employee employee = EmployeeCovert.convertEmployeeDTOToEmployee(employeeDTO);
            employee.setCompany(company.get());
            Employee employeeCreate = employeeRepository.save(employee);
            employeeDTO.setId(employeeCreate.getId());
            return employeeDTO;
        }
        return new EmployeeDTO();
    }

    public Optional<EmployeeDTO> updateEmployee(Long employeeId, EmployeeDTO employeeDTO) {
        Optional<Company> company = companyRepository.findById(employeeDTO.getCompanyDTO().getId());
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            employee.get().setFirstName(employeeDTO.getFirstName());
            employee.get().setLastName(employeeDTO.getLastName());
            employee.get().setEmail(employeeDTO.getEmail());
            employee.get().setAddress(employeeDTO.getAddress());
            employee.get().setStatus(employeeDTO.getStatus());
            employee.get().setPhoneNumber(employeeDTO.getPhoneNumber());
            if (company.isPresent())
                employee.get().setCompany(company.get());

            employeeRepository.save(employee.get());
            return Optional.of(employeeDTO);
        }
        return Optional.empty();
    }

}
