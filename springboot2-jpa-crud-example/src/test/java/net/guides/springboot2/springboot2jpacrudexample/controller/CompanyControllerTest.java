package net.guides.springboot2.springboot2jpacrudexample.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.guides.springboot2.springboot2jpacrudexample.dto.CompanyDTO;
import net.guides.springboot2.springboot2jpacrudexample.entity.Company;
import net.guides.springboot2.springboot2jpacrudexample.exception.GlobalExceptionHandler;
import net.guides.springboot2.springboot2jpacrudexample.repository.CompanyRepository;
import net.guides.springboot2.springboot2jpacrudexample.service.CompanyService;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CompanyController.class)
public class CompanyControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CompanyService companyService;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyController companyController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(companyController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilters(new CORSFilter())
                .build();
    }

    @Test
    @DisplayName("Test findAll()")
    public void test_get_all_company_success() throws Exception {
        List<CompanyDTO> companies = Arrays.asList(
                new CompanyDTO(1, "abc1", "ha noi", 1),
                new CompanyDTO(2, "abc2", "ha noi", 1),
                new CompanyDTO(3, "abc3", "ha noi", 1));

        when(companyService.findAllCompany()).thenReturn(Optional.of(companies));

        mockMvc.perform(get("/api/v1/companies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(200)));

        verify(companyService, times(1)).findAllCompany();
        verifyNoMoreInteractions(companyService);
    }

    @Test
    @DisplayName("Test findAll() fail")
    public void test_get_all_company_fail_no_content() throws Exception {
        when(companyService.findAllCompany()).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/v1/companies"))
                .andExpect(status().isNoContent());

        verify(companyService, times(1)).findAllCompany();
        verifyNoMoreInteractions(companyService);
    }

    @Test
    public void test_get_company_by_id_success() throws Exception {
        CompanyDTO companyDTO = new CompanyDTO(1, "abc1", "ha noi", 1);

        when(companyService.getCompanyById(anyLong())).thenReturn(Optional.of(companyDTO));

        mockMvc.perform(get("/api/v1/companies/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.companyName", is("abc1")))
                .andExpect(jsonPath("$.address", is("ha noi")))
                .andExpect(jsonPath("$.status", is(1)));

        verify(companyService, times(1)).getCompanyById(1l);
        verifyNoMoreInteractions(companyService);
    }

    @Test
    public void test_get_company_by_id_fail_404_not_found() throws Exception {

        when(companyService.getCompanyById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/companies/{id}", 100))
                .andExpect(status().isNotFound());

        verify(companyService, times(1)).getCompanyById(100l);
        verifyNoMoreInteractions(companyService);
    }

    @Test
    public void test_create_company_success() throws Exception {
        CompanyDTO companyDTO = new CompanyDTO(1,"abc1", "ha noi", 1);

        when(companyService.checkExist(companyDTO)).thenReturn(false);
        when(companyService.createCompany(any(CompanyDTO.class))).thenReturn(Optional.of(companyDTO));


        mockMvc.perform(
                post("/api/v1/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(companyDTO)))
                .andExpect(status().isOk());
        /*.andExpect(header().string("location", containsString("http://localhost/api/v1/companies")));*/

        /*verify(companyService, times(1)).exists(user);
        verify(companyService, times(1)).createCompany(any(CompanyDTO.class));
        verifyNoMoreInteractions(companyService);*/
    }

    @Test
    public void test_create_company_fail_409_conflict() throws Exception {
        CompanyDTO companyDTO = new CompanyDTO("abc1", "ha noi", 1);

        when(companyService.checkExist(companyDTO)).thenReturn(true);

        mockMvc.perform(
                post("/api/v1/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(companyDTO)))
                .andExpect(status().isConflict());

        verify(companyService, times(1)).checkExist(companyDTO);
        verifyNoMoreInteractions(companyService);
    }

    @Test
    public void test_update_company_success() throws Exception {
        CompanyDTO companyDTO = new CompanyDTO(1, "abc1", "ha noi", 1);

        when(companyService.getCompanyById(anyLong())).thenReturn(Optional.of(companyDTO));
        doReturn(Optional.of(companyDTO)).when(companyService).updateCompany(companyDTO.getId(), companyDTO);


        mockMvc.perform(
                put("/api/v1/companies/{id}", companyDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(companyDTO)))
                .andExpect(status().isOk());
        /*verify(companyService, times(1)).getCompanyById(companyDTO.getId());*/
        verify(companyService, times(1)).updateCompany(companyDTO.getId(), companyDTO);
        verifyNoMoreInteractions(companyService);
    }

    @Test
    public void test_update_company_fail_404_not_found() throws Exception {
        CompanyDTO companyDTO = new CompanyDTO(1, "abc1", "ha noi", 1);
        when(companyService.getCompanyById(anyLong())).thenReturn(Optional.empty());
        mockMvc.perform(put("/api/v1/companies/{id}", companyDTO.getId()));

        verifyNoMoreInteractions(companyService);
    }

    @Test
    public void test_delete_company_success() throws Exception {
        Company company = new Company(1, "abc1", "ha noi", 1);

        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        doNothing().when(companyRepository).delete(company);

        mockMvc.perform(
                delete("/api/v1/companies/{id}", company.getId()))
                .andExpect(status().isOk());
        /*verify(companyService, times(1)).getCompanyById(companyDTO.getId());
        verify(companyService, times(1)).updateCompany(companyDTO.getId(), companyDTO);
        verifyNoMoreInteractions(companyService);*/
    }

    @Test
    public void test_delete_company_fail_404_not_found() throws Exception {
        CompanyDTO companyDTO = new CompanyDTO(1, "abc1", "ha noi", 1);

        when(companyRepository.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(
                delete("/api/v1/companies/{id}", companyDTO.getId()))
                .andExpect(status().isNotFound());

        /*verify(companyService, times(1)).getCompanyById(companyDTO.getId());
        verifyNoMoreInteractions(companyService);*/
    }


    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
