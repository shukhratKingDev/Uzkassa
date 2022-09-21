package uz.uzkassa.uzkassa.service;

import uz.uzkassa.uzkassa.dto.EmployeeDto;
import uz.uzkassa.uzkassa.dto.ResponseDto;
import uz.uzkassa.uzkassa.entity.Employee;

import java.util.List;

public interface EmployeeService {
    ResponseDto add(EmployeeDto employeeDto);

    ResponseDto update(Long id,EmployeeDto employeeDto);

    ResponseDto delete(Long id);

    Employee get(Long id);

    List<Employee> getAll();
}
