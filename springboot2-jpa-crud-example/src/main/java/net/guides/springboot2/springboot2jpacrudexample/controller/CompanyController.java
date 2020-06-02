package net.guides.springboot2.springboot2jpacrudexample.controller;

import net.guides.springboot2.springboot2jpacrudexample.convertDTO.CompanyConvert;
import net.guides.springboot2.springboot2jpacrudexample.dto.CompanyDTO;
import net.guides.springboot2.springboot2jpacrudexample.entity.Company;
import net.guides.springboot2.springboot2jpacrudexample.exception.APIResponse;
import net.guides.springboot2.springboot2jpacrudexample.exception.FileDuplicateException;
import net.guides.springboot2.springboot2jpacrudexample.exception.ResourceNotFoundException;
import net.guides.springboot2.springboot2jpacrudexample.repository.CompanyRepository;
import net.guides.springboot2.springboot2jpacrudexample.service.CompanyService;
import net.guides.springboot2.springboot2jpacrudexample.ultil.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class CompanyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyService companyService;

    @GetMapping("/companies")
    @PreAuthorize("permitAll()")
    @Cacheable(value = "companies", unless = "#result.body.body.size() == 0")
    public ResponseEntity<APIResponse<List<CompanyDTO>>> getAllCompanies() {
        Optional<List<CompanyDTO>> companyDTOS = companyService.findAllCompany();
        if (companyDTOS.isPresent()) {
            return ResponseEntity.ok(new APIResponse<>(HttpStatus.OK.value(), "get all company success!", companyDTOS.get()));
        }
        LOGGER.info(" No company found!");
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/companies/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Cacheable(value = "companies", key = "#companyId")
    public ResponseEntity<CompanyDTO> getEmployeeById(@PathVariable(value = "id") Long companyId)
            throws ResourceNotFoundException {

        LOGGER.info("Find by id company :: ", companyId);
        CompanyDTO companyDTO = companyService.getCompanyById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + companyId));
        LOGGER.info("Find by id company success!");
        return ResponseEntity.ok().body(companyDTO);
    }

    @PostMapping("/companies")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @CachePut(value = "company", key = "#companyDTO.id")
    public ResponseEntity<CompanyDTO> createCompany(@Valid @RequestBody CompanyDTO companyDTO) {

        if (companyDTO != null && companyService.checkExist(companyDTO))
            throw new FileDuplicateException("Company is already exist!");
        LOGGER.info("starting save company...");
        Optional<CompanyDTO> createCompany = companyService.createCompany(companyDTO);
        return ResponseEntity.ok().body(createCompany.get());
    }

    @PutMapping("/companies/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @CachePut(value = "company", key = "#companyDTO.id")
    public ResponseEntity<CompanyDTO> updateCompany(@PathVariable(value = "id") Long companyId,
                                                    @Valid @RequestBody CompanyDTO companyDTO) throws ResourceNotFoundException {

        LOGGER.info("starting update company...");
        CompanyDTO updateCompany = companyService.updateCompany(companyId, companyDTO)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.COMPANY_NOT_FOUNT + companyId));
        return ResponseEntity.ok(updateCompany);
    }

    @DeleteMapping("/companies/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @CacheEvict(value = "companies", allEntries = true)
    public ResponseEntity<CompanyDTO> deleteCompany(@PathVariable(value = "id") Long companyId)
            throws ResourceNotFoundException {
        LOGGER.info("start get company by id!");
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.COMPANY_NOT_FOUNT + companyId));
        companyRepository.delete(company);
        LOGGER.info("delete company success!");
        return ResponseEntity.ok(CompanyConvert.convertCompanyToCompanyDTO(company));
    }
}
