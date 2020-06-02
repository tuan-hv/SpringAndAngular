package net.guides.springboot2.springboot2jpacrudexample.service;


import net.guides.springboot2.springboot2jpacrudexample.convertDTO.CompanyConvert;
import net.guides.springboot2.springboot2jpacrudexample.dto.CompanyDTO;
import net.guides.springboot2.springboot2jpacrudexample.entity.Company;
import net.guides.springboot2.springboot2jpacrudexample.repository.CompanyRepository;
import org.junit.Assert;
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
public class CompanyServiceTest {

    @InjectMocks
    private CompanyService companyService;

    @Mock
    private CompanyRepository companyRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_get_all_company_success() {
        List<Company> companies = new ArrayList<>();

        companies.add(new Company(1, "abc1", "ha noi", 1));
        companies.add(new Company(2, "abc2", "ha noi", 1));
        companies.add(new Company(3, "abc3", "ha noi", 1));

        when(companyRepository.findAll()).thenReturn(companies);

        Optional<List<CompanyDTO>> companyDTOS = companyService.findAllCompany();

        assertEquals(3, companyDTOS.get().size());
        verify(companyRepository, times(1)).findAll();
    }

    @Test
    public void test_get_all_company_not_found() {
        when(companyRepository.findAll()).thenReturn(null);
        Optional<List<CompanyDTO>> listProduct = companyService.findAllCompany();
        Assert.assertEquals(listProduct, Optional.empty());
    }

    @Test
    public void test_get_company_by_id_success() {
        when(companyRepository.findById(1L)).thenReturn(Optional.of(new Company(1, "abc1", "ha noi", 1)));

        Optional<CompanyDTO> companyDTO = companyService.getCompanyById(1l);

        assertEquals("abc1", companyDTO.get().getCompanyName());
        assertEquals("ha noi", companyDTO.get().getAddress());
    }

    @Test
    public void test_get_company_by_id_fail_404_not_found() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.empty());
        Optional<CompanyDTO> companyDTO = companyService.getCompanyById(111L);
        assertEquals(Optional.empty(), companyDTO);
    }

    @Test
    public void test_create_company_sucess() {
        Company company = new Company("abc1", "ha noi", 1);

        when(companyRepository.save(any(Company.class))).thenAnswer((Answer<Company>) invocation -> {
            Company company1 = (Company) invocation.getArguments()[0];
            company1.setId(1);
            return company1;
        });

        assertEquals(0, company.getId());

        Optional<CompanyDTO> createCompany = companyService.createCompany(CompanyConvert.convertCompanyToCompanyDTO(company));

        assertNotNull(createCompany.get().getId());

        assertEquals(1, createCompany.get().getId());
    }

    @Test
    public void test_create_company_throw_exception() {
        Company company = new Company();
        when(companyRepository.save(any(Company.class))).thenThrow(Exception.class);
        Optional<CompanyDTO> createCompany = companyService.createCompany(CompanyConvert.convertCompanyToCompanyDTO(company));
        assertEquals(Optional.empty(), createCompany);
    }

    @Test
    public void test_update_company_sucess() {
        Company company = new Company(1, "abc1", "ha noi", 1);
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));

        Optional<CompanyDTO> updateCompany = companyService.updateCompany(company.getId(), CompanyConvert.convertCompanyToCompanyDTO(company));

        assertThat(updateCompany, is(notNullValue()));
        assertEquals(updateCompany.get().getId(), 1);
    }

    @Test
    public void test_update_company_fail_404_not_found() {
        Company company = new Company(1, "abc1", "ha noi", 1);
        when(companyRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<CompanyDTO> updateCompany = companyService.updateCompany(company.getId(), CompanyConvert.convertCompanyToCompanyDTO(company));

        assertEquals(updateCompany, Optional.empty());
    }

}
