package uz.uzkassa.uzkassa.service;

import uz.uzkassa.uzkassa.dto.EmployeeDto;
import uz.uzkassa.uzkassa.dto.ResponseDto;
import uz.uzkassa.uzkassa.entity.Employee;

import java.util.List;

public interface EmployeeService {
    ResponseDto addEmployee(EmployeeDto employeeDto);

    ResponseDto editEmployee(Long id,EmployeeDto employeeDto);

    ResponseDto deleteEmployee(Long id);

    Employee getEmployeeById(Long id);

    List<Employee> getAllEmployees();
}
