package net.guides.springboot2.springboot2jpacrudexample.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "company")
@Data
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty(message = "{validation.company.name.notEmpty}")
    @Column(name = "company_name", nullable = false)
    private String companyName;
    @Column(name = "address")
    private String address;
    @Column(name = "status")
    private int status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
    private Set<Employee> listEmployee = new HashSet<>();

    public Company( String companyName, String address, int status) {
        this.companyName = companyName;
        this.address = address;
        this.status = status;
    }
    public Company(long id, String companyName, String address, int status) {
        this.id = id;
        this.companyName = companyName;
        this.address = address;
        this.status = status;
    }

}
