package uz.uzkassa.uzkassa.service.impl;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.uzkassa.uzkassa.dto.CompanyDto;
import uz.uzkassa.uzkassa.dto.ResponseDto;
import uz.uzkassa.uzkassa.entity.Company;
import uz.uzkassa.uzkassa.entity.Employee;
import uz.uzkassa.uzkassa.exceptions.NotAllowedException;

import uz.uzkassa.uzkassa.repository.CompanyRepository;
import uz.uzkassa.uzkassa.service.CompanyService;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PUBLIC)
public class CompanyServiceImpl implements CompanyService {

    CompanyRepository companyRepository;

    @Override
    public ResponseDto edit(Long id, CompanyDto companyDto) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new NotAllowedException());
        if (!company.getId().equals(getCurrentCompanyId())) {
            return new ResponseDto("This is not your company, You can not modify it", false);
        }
        company.setCompanyName(companyDto.getCompanyName());
        company.setCompanyAddress(companyDto.getCompanyAddress());
        company.setCompanyZipCode(companyDto.getCompanyZipCode());
        companyRepository.save(company);
        return new ResponseDto("Company information successfully modified", true);
    }

    @Override
    public ResponseDto block(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new NotAllowedException());
        if (!company.getId().equals(getCurrentCompanyId())) {
            return new ResponseDto("This is not your company, You can not modify it", false);
        }
        company.setBlocked(true);
        companyRepository.save(company);
        return new ResponseDto("Successfully blocked", true);
    }

    public Long getCurrentCompanyId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee employee = (Employee) authentication.getPrincipal();
        return employee.getCompany().getId();
    }
}
