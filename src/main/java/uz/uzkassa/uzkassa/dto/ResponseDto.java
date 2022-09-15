package uz.uzkassa.uzkassa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDto {
    private String message;
    private boolean success;
    private String token;

    public ResponseDto(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
