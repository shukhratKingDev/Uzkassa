package uz.uzkassa.uzkassa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@AllArgsConstructor
@Data
@NoArgsConstructor
public class SignUpDto {
    @NotNull(message = "firstName can not be empty")
    private String firstName;
    @NotNull(message = "lastName can not be empty")
    private String lastName;
    @NotNull(message = "username can not be empty")
    @Size(min = 5, message = "the length of username can not be less than 5")
    @Email
    private String username;
    @NotNull(message = "password can not be empty")
    @Size(min = 7, message = "the length of password can not be less than 7")
    private String password;
    @NotNull(message = "companyName can not be empty")
    private String companyName;
    @NotNull(message = "companyAddress can not be empty")
    private String companyAddress;
    @NotNull(message = "companyZipCode can not be empty")
    private String companyZipCode;
}
