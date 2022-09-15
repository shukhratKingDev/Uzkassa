package uz.uzkassa.uzkassa.service;

import uz.uzkassa.uzkassa.dto.SignIn;
import uz.uzkassa.uzkassa.dto.ResponseDto;
import uz.uzkassa.uzkassa.dto.SignUpDto;
import uz.uzkassa.uzkassa.dto.VerificationDto;

public interface AuthService  {

    ResponseDto signUp(SignUpDto signUpDto);

    ResponseDto signIn(SignIn signIn);

    ResponseDto verifyEmail(String email, String emailCode);
}
