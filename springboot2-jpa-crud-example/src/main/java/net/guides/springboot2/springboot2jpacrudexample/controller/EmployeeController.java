package net.guides.springboot2.springboot2jpacrudexample.controller;

import net.guides.springboot2.springboot2jpacrudexample.convertDTO.EmployeeCovert;
import net.guides.springboot2.springboot2jpacrudexample.dto.EmployeeDTO;
import net.guides.springboot2.springboot2jpacrudexample.entity.Employee;
import net.guides.springboot2.springboot2jpacrudexample.exception.ResourceNotFoundException;
import net.guides.springboot2.springboot2jpacrudexample.repository.EmployeeRepository;
import net.guides.springboot2.springboot2jpacrudexample.service.EmployeeService;
import net.guides.springboot2.springboot2jpacrudexample.ultil.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/employees")
	@PreAuthorize("permitAll()")
	@Cacheable( value = "employees", unless= "#result.body.size() == 0")
	public List<EmployeeDTO> getAllEmployees() {
		return employeeService.findAllEmployee();
	}

	@GetMapping("/employees/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@Cacheable(value = "companies", key = "#companyId")
	public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		LOGGER.info("Find by id employee :: ", employeeId);
		EmployeeDTO employeeDTO = employeeService.getEmployeeById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException(Constant.EMPLOYEE_NOT_FOUNT + employeeId));
		LOGGER.info("Find by id employee success!");
		return ResponseEntity.ok().body(employeeDTO);
	}

	@PostMapping("/employees")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@CachePut(value= "company", key= "#companyDTO.id")
	public EmployeeDTO createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
		LOGGER.info("starting save employee...");
		return employeeService.createEmployee(employeeDTO);
	}

	@PutMapping("/employees/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@CachePut(value= "company", key= "#companyDTO.id")
	public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable(value = "id") Long employeeId,
			@Valid @RequestBody EmployeeDTO employeeDTO) throws ResourceNotFoundException {
		LOGGER.info("starting update employee...");
		EmployeeDTO updateEmployeeDTO = employeeService.updateEmployee(employeeId, employeeDTO)
				.orElseThrow(() -> new ResourceNotFoundException(Constant.EMPLOYEE_NOT_FOUNT + employeeId));
		return ResponseEntity.ok(updateEmployeeDTO);
	}

	@DeleteMapping("/employees/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@CacheEvict(value = "companies", allEntries=true)
	public ResponseEntity<EmployeeDTO> deleteEmployee(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		LOGGER.info("start get employee by id!");
		Employee deleteEmployee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException(Constant.EMPLOYEE_NOT_FOUNT + employeeId));

		employeeRepository.delete(deleteEmployee);
		LOGGER.info("delete employee success!");
		return ResponseEntity.ok(EmployeeCovert.convertEmployeeToEmployeeDTO(deleteEmployee));
	}
}
