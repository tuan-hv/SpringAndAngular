package net.guides.springboot2.springboot2jpacrudexample.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phoneNumber;
    private int status;

    private CompanyDTO companyDTO;

}
