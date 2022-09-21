package uz.uzkassa.uzkassa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uzkassa.uzkassa.entity.Employee;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
boolean existsByUsername(String username);
Optional<Employee> findByUsername(String username);
Optional<Employee> findByUsernameAndEmailCode(String username, String emailCode);
Optional<Employee>findByUsernameAndIdNot(String username, Long id);
List<Employee>findAllByCompanyId(Long companyId);
}
