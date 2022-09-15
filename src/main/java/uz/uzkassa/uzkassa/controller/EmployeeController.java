package uz.uzkassa.uzkassa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uzkassa.uzkassa.dto.EmployeeDto;
import uz.uzkassa.uzkassa.dto.ResponseDto;
import uz.uzkassa.uzkassa.entity.Employee;
import uz.uzkassa.uzkassa.service.EmployeeService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    private EmployeeService employeeService;
@Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/add")
    public HttpEntity<ResponseDto> addEmployee( @Valid @RequestBody EmployeeDto employeeDto){
       ResponseDto responseDto= employeeService.addEmployee(employeeDto);
        return ResponseEntity.status(responseDto.isSuccess()? HttpStatus.CREATED:HttpStatus.UNAUTHORIZED).body(responseDto);

    }

    @PutMapping("/{id}")
    public HttpEntity<ResponseDto>editEmployee(@PathVariable Long id,@PathVariable EmployeeDto employeeDto){
    ResponseDto responseDto=employeeService.editEmployee(id,employeeDto);
     return ResponseEntity.status(responseDto.isSuccess()?HttpStatus.OK:HttpStatus.NOT_FOUND).body(responseDto);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<ResponseDto> deleteEmployee(@PathVariable Long id){
    ResponseDto responseDto=employeeService.deleteEmployee(id);
    return ResponseEntity.status(responseDto.isSuccess()?HttpStatus.OK:HttpStatus.NOT_FOUND).body(responseDto);
    }


    @GetMapping("/{id}")
    public HttpEntity<Employee> getEmployeeById(@PathVariable Long id){
    Employee employee=employeeService.getEmployeeById(id);
    return ResponseEntity.status(employee != null ?HttpStatus.OK:HttpStatus.NOT_FOUND).body(employee);

    }

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees(){

        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getAllEmployees());
    }

}
