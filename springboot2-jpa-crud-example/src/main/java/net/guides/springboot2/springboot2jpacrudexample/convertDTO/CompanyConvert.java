package net.guides.springboot2.springboot2jpacrudexample.convertDTO;

import net.guides.springboot2.springboot2jpacrudexample.dto.CompanyDTO;
import net.guides.springboot2.springboot2jpacrudexample.entity.Company;

public class CompanyConvert {

    public static CompanyDTO convertCompanyToCompanyDTO(Company company){
        CompanyDTO companyDTO = new CompanyDTO();

        companyDTO.setId(company.getId());
        companyDTO.setCompanyName(company.getCompanyName());
        companyDTO.setAddress(company.getAddress());
        companyDTO.setStatus(company.getStatus());

        return companyDTO;
    }

    public static Company convertCompanyToCompanyDTO(CompanyDTO companyDTO){
        Company company = new Company();

        company.setId(companyDTO.getId());
        company.setCompanyName(companyDTO.getCompanyName());
        company.setAddress(companyDTO.getAddress());
        company.setStatus(companyDTO.getStatus());

        return company;
    }
}
