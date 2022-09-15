package uz.uzkassa.uzkassa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uzkassa.uzkassa.dto.CompanyDto;
import uz.uzkassa.uzkassa.dto.ResponseDto;
import uz.uzkassa.uzkassa.service.CompanyService;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
    private CompanyService companyService;
@Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> editCompany(@PathVariable Long id, @RequestBody CompanyDto companyDto){

        ResponseDto responseDto=companyService.editCompany(id,companyDto);
        return ResponseEntity.status(responseDto.isSuccess()? HttpStatus.OK:HttpStatus.NOT_FOUND).body(responseDto);
    }

    @PutMapping("/check/{id}")
    public ResponseEntity<ResponseDto>blockCompany(@PathVariable Long id){

    ResponseDto responseDto=companyService.blockCompany(id);
    return ResponseEntity.status(responseDto.isSuccess()?HttpStatus.OK:HttpStatus.NOT_FOUND).body(responseDto);
    }
}
