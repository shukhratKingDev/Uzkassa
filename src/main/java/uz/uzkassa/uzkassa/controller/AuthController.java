package uz.uzkassa.uzkassa.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uzkassa.uzkassa.dto.ResponseDto;
import uz.uzkassa.uzkassa.dto.SignIn;
import uz.uzkassa.uzkassa.dto.SignUpDto;
import uz.uzkassa.uzkassa.dto.VerificationDto;
import uz.uzkassa.uzkassa.service.AuthService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@FieldDefaults(makeFinal = true)

public class AuthController {
    AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseDto> signUp(@Valid @RequestBody SignUpDto signUpDto) {

        ResponseDto responseDto = authService.signUp(signUpDto);
        return ResponseEntity.status(responseDto.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(responseDto);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ResponseDto> signIn(@Valid @RequestBody SignIn signIn) {
        ResponseDto responseDto = authService.signIn(signIn);
        return ResponseEntity.status(responseDto.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(responseDto);
    }

    @PostMapping("/verify-email")
    public ResponseEntity<ResponseDto> verifyEmail(@RequestBody VerificationDto verificationDto) {
        ResponseDto responseDto = authService.verifyEmail(verificationDto);
        return ResponseEntity.status(responseDto.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(responseDto);
    }


}
