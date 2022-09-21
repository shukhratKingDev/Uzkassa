package uz.uzkassa.uzkassa.service;

import uz.uzkassa.uzkassa.dto.CompanyDto;
import uz.uzkassa.uzkassa.dto.ResponseDto;

public interface CompanyService {

    ResponseDto edit(Long id, CompanyDto companyDto);

    ResponseDto block(Long id);
}
