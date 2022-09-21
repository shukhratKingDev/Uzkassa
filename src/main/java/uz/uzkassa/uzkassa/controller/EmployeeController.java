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
@RequestMapping("/api/employees")
public class EmployeeController {
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping()
    public HttpEntity<ResponseDto> add(@Valid @RequestBody EmployeeDto employeeDto) {
        ResponseDto responseDto = employeeService.add(employeeDto);
        return ResponseEntity.status(responseDto.isSuccess() ? HttpStatus.CREATED : HttpStatus.UNAUTHORIZED).body(responseDto);

    }

    @PutMapping("/{id}")
    public HttpEntity<ResponseDto> update(@PathVariable Long id, @RequestBody EmployeeDto employeeDto) {
        ResponseDto responseDto = employeeService.update(id, employeeDto);
        return ResponseEntity.status(responseDto.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(responseDto);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<ResponseDto> delete(@PathVariable Long id) {
        ResponseDto responseDto = employeeService.delete(id);
        return ResponseEntity.status(responseDto.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(responseDto);
    }


    @GetMapping("/{id}")
    public HttpEntity<Employee> get(@PathVariable Long id) {
        Employee employee = employeeService.get(id);
        return ResponseEntity.status(employee != null ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(employee);

    }

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAll() {

        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getAll());
    }

}
