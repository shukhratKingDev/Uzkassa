package uz.uzkassa.uzkassa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uzkassa.uzkassa.dto.ResponseDto;
import uz.uzkassa.uzkassa.dto.SignIn;
import uz.uzkassa.uzkassa.dto.SignUpDto;
import uz.uzkassa.uzkassa.service.AuthService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
@PostMapping("/signUp")
    public ResponseEntity<ResponseDto> signUp(@Valid @RequestBody SignUpDto signUpDto){

        ResponseDto responseDto=authService.signUp(signUpDto);
        return ResponseEntity.status(responseDto.isSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(responseDto);
    }
@PostMapping("/signIn")
    public ResponseEntity<ResponseDto> signIn(@Valid @RequestBody SignIn signIn){
        ResponseDto responseDto=authService.signIn(signIn);
        return ResponseEntity.status(responseDto.isSuccess()?HttpStatus.OK:HttpStatus.NOT_FOUND).body(responseDto);
    }

    @GetMapping("/verifyEmail")
    public ResponseEntity<ResponseDto>verifyEmail(@RequestParam String email,@RequestParam String emailCode){
    ResponseDto responseDto=authService.verifyEmail(email,emailCode);
    return ResponseEntity.status(responseDto.isSuccess()?HttpStatus.OK:HttpStatus.NOT_FOUND).body(responseDto);
    }


}
