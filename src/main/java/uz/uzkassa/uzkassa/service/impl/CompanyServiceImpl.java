package uz.uzkassa.uzkassa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.uzkassa.uzkassa.dto.CompanyDto;
import uz.uzkassa.uzkassa.dto.ResponseDto;
import uz.uzkassa.uzkassa.entity.Company;
import uz.uzkassa.uzkassa.entity.enums.SystemRoleName;
import uz.uzkassa.uzkassa.repository.CompanyRepository;
import uz.uzkassa.uzkassa.service.CompanyService;

import java.util.Optional;
@Service
public class CompanyServiceImpl implements CompanyService {
    private CompanyRepository companyRepository;
    private EmployeeServiceImpl employeeService;
@Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, EmployeeServiceImpl employeeService) {
        this.companyRepository = companyRepository;
    this.employeeService = employeeService;
}

    @Override
    public ResponseDto editCompany(Long id, CompanyDto companyDto) {
      ResponseDto responseDto=check(id);
        if (!responseDto.isSuccess()) {
           return responseDto;
        }

        Company company=companyRepository.findById(id).get();
        company.setCompanyName(companyDto.getCompanyName());
        company.setCompanyAddress(companyDto.getCompanyAddress());
        company.setCompanyZipCode(companyDto.getCompanyZipCode());
        companyRepository.save(company);
        return new ResponseDto("Company information successfully modified",true);
    }

    @Override
    public ResponseDto blockCompany(Long id) {
    ResponseDto responseDto=check(id);
        if (!responseDto.isSuccess()) {
            return responseDto;
        }

        Company company=companyRepository.findById(id).get();
        company.setBlocked(true);
        companyRepository.save(company);
        return null;
    }

    public ResponseDto check(Long id){
        if (!employeeService.authorize().equals(SystemRoleName.SYSTEM_ROLE_ADMIN)) {
            return new ResponseDto("This operation can be done only by Admin",false);
        }

        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (!optionalCompany.isPresent()) {
            return new ResponseDto("Company with this id not found",false);
        }
        if (!employeeService.getCompany().equals(optionalCompany.get())) {
            return new ResponseDto("This is not your company, You can not modify it", false);
        }
        return new ResponseDto("Ok",true);

    }
}
