package uz.uzkassa.uzkassa.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uzkassa.uzkassa.dto.CompanyDto;
import uz.uzkassa.uzkassa.dto.ResponseDto;
import uz.uzkassa.uzkassa.service.CompanyService;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(makeFinal = true)
public class CompanyController {
    CompanyService companyService;

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> edit(@PathVariable Long id, @RequestBody CompanyDto companyDto) {

        ResponseDto responseDto = companyService.edit(id, companyDto);
        return ResponseEntity.status(responseDto.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(responseDto);
    }

    @PutMapping("/check/{id}")
    public ResponseEntity<ResponseDto> block(@PathVariable Long id) {

        ResponseDto responseDto = companyService.block(id);
        return ResponseEntity.status(responseDto.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(responseDto);
    }
}
