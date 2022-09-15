package uz.uzkassa.uzkassa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignIn {
    @Email
    @NotNull
    @Size(min = 5,message = "the length of username can not be less than 5")
    private String username;
    @Size(min = 7,message = "the length of password can not be less than 5")
    private String password;
}
