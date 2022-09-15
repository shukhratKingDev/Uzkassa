package uz.uzkassa.uzkassa.dto;

import lombok.Data;

@Data
public class VerificationDto {
    private String email;
    private String emailCode;
}
