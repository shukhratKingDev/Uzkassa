package uz.uzkassa.uzkassa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
public class VerificationDto {
    @Email
    private String email;
    private String emailCode;
}
