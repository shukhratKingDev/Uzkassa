package uz.uzkassa.uzkassa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.uzkassa.uzkassa.dto.EmployeeDto;
import uz.uzkassa.uzkassa.dto.ResponseDto;
import uz.uzkassa.uzkassa.entity.Company;
import uz.uzkassa.uzkassa.entity.Employee;
import uz.uzkassa.uzkassa.entity.enums.SystemRoleName;
import uz.uzkassa.uzkassa.exceptions.UnAuthorizedOperationException;
import uz.uzkassa.uzkassa.repository.EmployeeRepository;
import uz.uzkassa.uzkassa.service.EmployeeService;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;
@Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public ResponseDto addEmployee(EmployeeDto employeeDto) {
        if (!authorize().equals(SystemRoleName.SYSTEM_ROLE_ADMIN)) {
            return new ResponseDto("You can not do this operation. This can be done only by Admin",false);
        }

        if (employeeRepository.existsByUsername(employeeDto.getUsername())) {
            return new ResponseDto("This username already exist.",false);
        }
        Employee employee=new Employee(
                employeeDto.getFirstName(),employeeDto.getLastName(),employeeDto.getUsername()
                ,SystemRoleName.SYSTEM_ROLE_USER,getCompany()
        );
        employeeRepository.save(employee);
        return new ResponseDto("Employee save successfully",true);

    }

    @Override
    public ResponseDto editEmployee(Long id,EmployeeDto employeeDto) {
        if (authorize().equals(SystemRoleName.SYSTEM_ROLE_ADMIN)) {
            return new ResponseDto("You can not do this operation. This can be done only by Admin",false);
        }
        Optional<Employee> optionalEmployee =  employeeRepository.findById(id);
        if (!optionalEmployee.isPresent()) {
            return new ResponseDto("Employee with this id not found",false);
        }

        Employee employee=optionalEmployee.get();
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setUsername(employeeDto.getUsername());
        employee.setEnabled(employeeDto.isEnabled());
        return new ResponseDto("Employee information successfully updated",true);
    }

    @Override
    public ResponseDto deleteEmployee(Long id) {
        if (!authorize().equals(SystemRoleName.SYSTEM_ROLE_ADMIN)) {
            return new ResponseDto("You can not do this operation. This can be done only by Admin",false);
        }
        if (!employeeRepository.findById(id).isPresent()) {
            return new ResponseDto("Employee with this id not found",false);
        }
        employeeRepository.deleteById(id);

        return new ResponseDto("Employee successfully deleted",true);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        if (authorize().equals(SystemRoleName.SYSTEM_ROLE_ADMIN)) {
            throw new UnAuthorizedOperationException("This operation can be done only by Admin");
        }
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (!optionalEmployee.isPresent()) {
            throw new BadCredentialsException("Employee with this id not found");
        }
        return optionalEmployee.get();
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public SystemRoleName authorize(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee employee=(Employee)authentication.getPrincipal();
        return employee.getRoleName();
    }

public Company getCompany(){

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Employee employee=(Employee)authentication.getPrincipal();
    return employee.getCompany();
}



}
