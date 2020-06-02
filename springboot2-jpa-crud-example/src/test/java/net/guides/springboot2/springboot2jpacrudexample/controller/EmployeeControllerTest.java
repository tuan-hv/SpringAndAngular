package net.guides.springboot2.springboot2jpacrudexample.controller;

import net.guides.springboot2.springboot2jpacrudexample.dto.CompanyDTO;
import net.guides.springboot2.springboot2jpacrudexample.dto.EmployeeDTO;
import net.guides.springboot2.springboot2jpacrudexample.entity.Company;
import net.guides.springboot2.springboot2jpacrudexample.entity.Employee;
import net.guides.springboot2.springboot2jpacrudexample.repository.EmployeeRepository;
import net.guides.springboot2.springboot2jpacrudexample.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static net.guides.springboot2.springboot2jpacrudexample.controller.CompanyControllerTest.asJsonString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = EmployeeControllerTest.class)
public class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeController employeeController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(employeeController)
                .addFilters(new CORSFilter())
                .build();
    }

    @Test
    @DisplayName("Test findAll()")
    public void test_get_all_employee_success() throws Exception {
        List<EmployeeDTO> employees = new ArrayList<>();

        CompanyDTO company1 = new CompanyDTO(1, "abc1", "ha noi", 1);
        CompanyDTO company2 = new CompanyDTO(2, "abc2", "ha noi", 1);
        CompanyDTO company3 = new CompanyDTO(3, "abc3", "ha noi", 1);

        employees.add(new EmployeeDTO(1, "nguyen 1", "hoang 1", "anwabo@gmail.com", "bac ninh", "12165131", 1, company1));
        employees.add(new EmployeeDTO(1, "nguyen 2", "hoang 2", "anwabo@gmail.com", "bac ninh", "12165131", 1, company2));
        employees.add(new EmployeeDTO(1, "nguyen 3", "hoang 3", "anwabo@gmail.com", "bac ninh", "12165131", 1, company3));

        when(employeeService.findAllEmployee()).thenReturn(employees);

        mockMvc.perform(get("/api/v1/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));

        verify(employeeService, times(1)).findAllEmployee();
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    public void test_get_employee_by_id_success() throws Exception {
        CompanyDTO company = new CompanyDTO(1, "abc1", "ha noi", 1);
        EmployeeDTO employee = new EmployeeDTO(1, "nguyen 1", "hoang 1", "anwabo@gmail.com", "bac ninh", "12165131", 1, company);

        when(employeeService.getEmployeeById(1l)).thenReturn(Optional.of(employee));

        mockMvc.perform(get("/api/v1/employees/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)));

        verify(employeeService, times(1)).getEmployeeById(1l);
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    public void test_get_employee_by_id_fail_404_not_found() throws Exception {

        when(employeeService.getEmployeeById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/employees/{id}", 100))
                .andExpect(status().isNotFound());

        verify(employeeService, times(1)).getEmployeeById(100l);
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    public void test_create_employee_success() throws Exception {
        CompanyDTO company = new CompanyDTO(1, "abc1", "ha noi", 1);
        EmployeeDTO employee = new EmployeeDTO(1, "nguyen 1", "hoang 1", "anwabo@gmail.com", "bac ninh", "12165131", 1, company);

        /*when(companyService.exists(user)).thenReturn(false);*/
        when(employeeService.createEmployee(any(EmployeeDTO.class))).thenReturn(employee);


        mockMvc.perform(
                post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(employee)))
                .andExpect(status().isOk());
        /*.andExpect(header().string("location", containsString("http://localhost/api/v1/companies")));*/

        /*verify(companyService, times(1)).exists(user);*/
        verify(employeeService, times(1)).createEmployee(any(EmployeeDTO.class));
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    public void test_update_employee_success() throws Exception {
        CompanyDTO company = new CompanyDTO(1, "abc1", "ha noi", 1);
        EmployeeDTO employee = new EmployeeDTO(1, "nguyen 1", "hoang 1", "anwabo@gmail.com", "bac ninh", "12165131", 1, company);

        when(employeeService.getEmployeeById(anyLong())).thenReturn(Optional.of(employee));
        doReturn(Optional.of(employee)).when(employeeService).updateEmployee(employee.getId(), employee);

        mockMvc.perform(
                put("/api/v1/employees/{id}", employee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(employee)))
                .andExpect(status().isOk());

        /*verify(employeeService, times(1)).getEmployeeById(anyLong());*/
        verify(employeeService, times(1)).updateEmployee(employee.getId(), employee);
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    public void test_update_employee_fail_404_not_found() throws Exception {
        CompanyDTO company = new CompanyDTO(1, "abc1", "ha noi", 1);
        EmployeeDTO employee = new EmployeeDTO(1, "nguyen 1", "hoang 1", "anwabo@gmail.com", "bac ninh", "12165131", 1, company);

        when(employeeService.getEmployeeById(anyLong())).thenReturn(Optional.empty());
        mockMvc.perform(put("/api/v1/employees/{id}", employee.getId()));

        /*verify(employeeService, times(1)).updateEmployee(employee.getId(), employee);
        verifyNoMoreInteractions(employeeService);*/
    }

    @Test
    public void test_delete_employee_success() throws Exception {
        Company company = new Company(1, "abc1", "ha noi", 1);
        Employee employee = new Employee(1, "nguyen 1", "hoang 1", "anwabo@gmail.com", "bac ninh", "12165131", 1, company);

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).delete(employee);

        mockMvc.perform(
                delete("/api/v1/employees/{id}", company.getId()))
                .andExpect(status().isOk());
        /*verify(employeeService, times(1)).getEmployeeById(employee.getId());
        verifyNoMoreInteractions(employeeService);*/
    }

    @Test
    public void test_delete_employee_fail_404_not_found() throws Exception {
        CompanyDTO company = new CompanyDTO(1, "abc1", "ha noi", 1);
        EmployeeDTO employee = new EmployeeDTO(1, "nguyen 1", "hoang 1", "anwabo@gmail.com", "bac ninh", "12165131", 1, company);

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(
                delete("/api/v1/employees/{id}", employee.getId()))
                .andExpect(status().isNotFound());

        /*verify(employeeService, times(1)).getEmployeeById(employee.getId());
        verifyNoMoreInteractions(employeeService);*/
    }

}
