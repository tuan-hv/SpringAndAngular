package net.guides.springboot2.springboot2jpacrudexample.repository;

import net.guides.springboot2.springboot2jpacrudexample.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    boolean findByCompanyName(String companyName);
}
