package uz.uzkassa.uzkassa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uzkassa.uzkassa.entity.Company;


@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
