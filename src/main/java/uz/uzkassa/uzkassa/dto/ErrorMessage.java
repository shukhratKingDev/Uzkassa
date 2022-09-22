package uz.uzkassa.uzkassa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    private String userMessage;
    private String developerMessage;

}
