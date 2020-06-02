package net.guides.springboot2.springboot2jpacrudexample.service;

import net.guides.springboot2.springboot2jpacrudexample.convertDTO.CompanyConvert;
import net.guides.springboot2.springboot2jpacrudexample.convertDTO.EmployeeCovert;
import net.guides.springboot2.springboot2jpacrudexample.dto.CompanyDTO;
import net.guides.springboot2.springboot2jpacrudexample.dto.EmployeeDTO;
import net.guides.springboot2.springboot2jpacrudexample.entity.Company;
import net.guides.springboot2.springboot2jpacrudexample.entity.Employee;
import net.guides.springboot2.springboot2jpacrudexample.repository.CompanyRepository;
import net.guides.springboot2.springboot2jpacrudexample.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private CompanyRepository companyRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_get_all_employee_success() {
        List<Employee> employees = new ArrayList<>();

        Company company1 = new Company(1, "abc1", "ha noi", 1);
        Company company2 = new Company(2, "abc2", "ha noi", 1);
        Company company3 = new Company(3, "abc3", "ha noi", 1);

        employees.add(new Employee(1, "nguyen 1", "hoang 1", "anwabo@gmail.com", "bac ninh", "12165131", 1, company1));
        employees.add(new Employee(1, "nguyen 2", "hoang 2", "anwabo@gmail.com", "bac ninh", "12165131", 1, company2));
        employees.add(new Employee(1, "nguyen 3", "hoang 3", "anwabo@gmail.com", "bac ninh", "12165131", 1, company3));

        when(employeeRepository.findAll()).thenReturn(employees);

        List<EmployeeDTO> employeeDTOS = employeeService.findAllEmployee();

        assertEquals(3, employeeDTOS.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    public void test_get_employee_by_id_success() {
        Company company = new Company(1, "abc1", "ha noi", 1);
        Employee employee = new Employee(1, "nguyen 1", "hoang 1", "anwabo@gmail.com", "bac ninh", "12165131", 1, company);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Optional<EmployeeDTO> employeeDTO = employeeService.getEmployeeById(1l);

        assertEquals("anwabo@gmail.com", employeeDTO.get().getEmail());
    }

    @Test
    public void test_get_employee_by_id_fail_404_not_found() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<EmployeeDTO> employeeDTO = employeeService.getEmployeeById(111L);

        assertEquals(Optional.empty(), employeeDTO);
    }

    @Test
    public void test_create_employee_sucess() {
        CompanyDTO company = new CompanyDTO(1, "abc1", "ha noi", 1);
        EmployeeDTO employee = new EmployeeDTO();
        employee.setFirstName("nguyen 1");
        employee.setLastName("hoang 1");
        employee.setEmail("anwabo@gmail.com");
        employee.setAddress("bac ninh");
        employee.setPhoneNumber("12165131");
        employee.setStatus(1);
        employee.setCompanyDTO(company);

        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(CompanyConvert.convertCompanyToCompanyDTO(company)));
        when(employeeRepository.save(any(Employee.class))).thenAnswer((Answer<Employee>) invocation -> {
            Employee createEmployee = (Employee) invocation.getArguments()[0];
            createEmployee.setId(1);
            return createEmployee;
        });

        assertEquals(0, employee.getId());
        EmployeeDTO createEmployee = employeeService.createEmployee(employee);
        assertNotNull(createEmployee.getId());
        assertEquals(1, createEmployee.getId());
    }

    @Test
    public void test_create_employee_throw_exception() {
        CompanyDTO company = new CompanyDTO(1, "abc1", "ha noi", 1);
        EmployeeDTO employee = new EmployeeDTO();
        employee.setId(1);
        employee.setFirstName("nguyen 1");
        employee.setLastName("hoang 1");
        employee.setEmail("anwabo@gmail.com");
        employee.setAddress("bac ninh");
        employee.setPhoneNumber("12165131");
        employee.setStatus(1);
        employee.setCompanyDTO(company);

        when(companyRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(employeeRepository.save(any(Employee.class))).thenThrow(Exception.class);

        EmployeeDTO createEmployee = employeeService.createEmployee(employee);
        assertEquals(null, createEmployee);
    }

    @Test
    public void test_update_employee_sucess() {
        CompanyDTO company = new CompanyDTO(1, "abc1", "ha noi", 1);
        EmployeeDTO employee = new EmployeeDTO();
        employee.setId(1);
        employee.setFirstName("nguyen 1");
        employee.setLastName("hoang 1");
        employee.setEmail("anwabo@gmail.com");
        employee.setAddress("bac ninh");
        employee.setPhoneNumber("12165131");
        employee.setStatus(1);
        employee.setCompanyDTO(company);

        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(CompanyConvert.convertCompanyToCompanyDTO(company)));
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(EmployeeCovert.convertEmployeeDTOToEmployee(employee)));

        Optional<EmployeeDTO> updateEmployee = employeeService.updateEmployee(employee.getId(), employee);

        assertThat(updateEmployee, is(notNullValue()));
        assertEquals(updateEmployee.get().getId(), 1);
    }

    @Test
    public void test_update_employee_fail_404_not_found() {
        CompanyDTO company = new CompanyDTO(1, "abc1", "ha noi", 1);
        EmployeeDTO employee = new EmployeeDTO();
        employee.setId(1);
        employee.setFirstName("nguyen 1");
        employee.setLastName("hoang 1");
        employee.setEmail("anwabo@gmail.com");
        employee.setAddress("bac ninh");
        employee.setPhoneNumber("12165131");
        employee.setStatus(1);
        employee.setCompanyDTO(company);

        when(companyRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<EmployeeDTO> updateEmployee = employeeService.updateEmployee(employee.getId(), employee);

        assertEquals(updateEmployee, Optional.empty());
    }
}
