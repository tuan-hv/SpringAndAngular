package net.guides.springboot2.springboot2jpacrudexample.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class CompanyDTO {
    private long id;
    private String companyName;
    private String address;
    private int status;

    private Set<EmployeeDTO> listEmployee = new HashSet<>();

    public CompanyDTO(String companyName, String address, int status) {
        this.companyName = companyName;
        this.address = address;
        this.status = status;
    }

    public CompanyDTO(long id, String companyName, String address, int status) {
        this.id = id;
        this.companyName = companyName;
        this.address = address;
        this.status = status;
    }


}
