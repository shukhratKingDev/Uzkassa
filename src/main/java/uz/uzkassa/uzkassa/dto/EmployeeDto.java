package uz.uzkassa.uzkassa.dto;

import lombok.Data;

@Data
public class EmployeeDto {
    private String firstName;
    private String lastName;
    private String username;
    private boolean enabled;

    public EmployeeDto(String firstName, String lastName, String username,boolean enabled) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.enabled = enabled;
    }
}
