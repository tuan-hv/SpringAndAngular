package net.guides.springboot2.springboot2jpacrudexample.service;

import net.guides.springboot2.springboot2jpacrudexample.convertDTO.CompanyConvert;
import net.guides.springboot2.springboot2jpacrudexample.dto.CompanyDTO;
import net.guides.springboot2.springboot2jpacrudexample.entity.Company;
import net.guides.springboot2.springboot2jpacrudexample.repository.CompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);

    @Autowired
    private CompanyRepository companyRepository;

    public Optional<List<CompanyDTO>> findAllCompany() {
        List<Company> companies = companyRepository.findAll();
        if (companies != null) {
            LOGGER.info("get all company success!");
            List<CompanyDTO> companyDTOS = new ArrayList<>();
            companies.forEach(c ->
                companyDTOS.add(CompanyConvert.convertCompanyToCompanyDTO(c))
            );
            return Optional.of(companyDTOS);
        }
        LOGGER.info("get all company fail!");
        return Optional.empty();
    }

    public Optional<CompanyDTO> getCompanyById(Long companyId) {
        Optional<Company> company = companyRepository.findById(companyId);
        if (company.isPresent()) {
            LOGGER.info("get company by id success...");
            return Optional.of(CompanyConvert.convertCompanyToCompanyDTO(company.get()));
        }
        LOGGER.info("get company by id fail...");
        return Optional.empty();
    }

    public Optional<CompanyDTO> createCompany(CompanyDTO companyDTO) {
        try{
            Company employeeUpdate = companyRepository.save(CompanyConvert.convertCompanyToCompanyDTO(companyDTO));
            companyDTO.setId(employeeUpdate.getId());
            LOGGER.info("save company success!");
            return Optional.of(companyDTO);
        }
        catch (Exception e){
            LOGGER.error("error with save company ::",e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<CompanyDTO> updateCompany(Long companyId, CompanyDTO companyDTO) {
        Optional<Company> company = companyRepository.findById(companyId);
        if (company.isPresent()) {
            LOGGER.info("update company success!");
            company.get().setCompanyName(companyDTO.getCompanyName());
            company.get().setAddress(companyDTO.getAddress());
            company.get().setStatus(companyDTO.getStatus());
            companyRepository.save(company.get());
            return Optional.of(companyDTO);
        }
        LOGGER.info("update company success!");
        return Optional.empty();
    }

    public Boolean checkExist(CompanyDTO companyDTO){
        return companyRepository.findByCompanyName(companyDTO.getCompanyName());
    }

}
