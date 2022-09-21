package uz.uzkassa.uzkassa.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.uzkassa.uzkassa.dto.ResponseDto;
import uz.uzkassa.uzkassa.dto.SignIn;
import uz.uzkassa.uzkassa.dto.SignUpDto;
import uz.uzkassa.uzkassa.dto.VerificationDto;
import uz.uzkassa.uzkassa.entity.Company;
import uz.uzkassa.uzkassa.entity.Employee;
import uz.uzkassa.uzkassa.enums.SystemRoleName;
import uz.uzkassa.uzkassa.exceptions.EntityAlreadyExistException;
import uz.uzkassa.uzkassa.exceptions.ExpiredException;
import uz.uzkassa.uzkassa.exceptions.NotFoundException;
import uz.uzkassa.uzkassa.repository.EmployeeRepository;
import uz.uzkassa.uzkassa.security.JwtProvider;
import uz.uzkassa.uzkassa.service.AuthService;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService, UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Autowired
    public AuthServiceImpl(EmployeeRepository employeeRepository, JavaMailSender javaMailSender, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.employeeRepository = employeeRepository;
        this.javaMailSender = javaMailSender;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @Override
    @Transactional // if mailSender can not send message employee must not be saved
    public ResponseDto signUp(SignUpDto signUpDto) {
        if (employeeRepository.existsByUsername(signUpDto.getUsername())) {
            throw new EntityAlreadyExistException();
        }
        // employee information gathering
        Employee employee = new Employee();
        employee.setFirstName(signUpDto.getFirstName());
        employee.setLastName(signUpDto.getLastName());
        employee.setUsername(signUpDto.getUsername());
        employee.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        // company information gathering
        Company company = new Company(signUpDto.getCompanyName(), signUpDto.getCompanyAddress(), signUpDto.getCompanyZipCode());

        employee.setCompany(company);
        employee.setRoleName(SystemRoleName.SYSTEM_ROLE_ADMIN);
        String emailCode = String.valueOf(new Random().nextInt(99999999)).substring(4);
        employee.setEmailCode(emailCode);
        employeeRepository.save(employee);
        sendEmail(signUpDto.getUsername(), emailCode);
        return new ResponseDto("User successfully saved", true);
    }

    @Override
    public ResponseDto verifyEmail(VerificationDto verifyEmailDto) {
        Optional<Employee> optionalEmployee = employeeRepository.findByUsernameAndEmailCode(verifyEmailDto.getEmail(), verifyEmailDto.getEmailCode());
        if (!optionalEmployee.isPresent()) {
            throw new NotFoundException();
        }
        if (optionalEmployee.get().getEmailCode() == null) {
            throw new ExpiredException();
        }
        Employee employee = optionalEmployee.get();
        employee.setEmailCode(null);
        employee.setEnabled(true);
        employeeRepository.save(employee);
        return new ResponseDto("Email successfully verified.", true);
    }

    @Override
    public ResponseDto signIn(SignIn signIn) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signIn.getUsername(), signIn.getPassword()));
            Employee employee = (Employee) authentication.getPrincipal();
            String token = jwtProvider.generateJwt(employee.getUsername());
            return new ResponseDto("You successfully logged in", true, token);
        } catch (Exception e) {
            throw new BadCredentialsException("Username or email is invalid");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return employeeRepository.findByUsername(username).orElseThrow();
    }


    public void sendEmail(String email, String emailCode) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("shukhrat1201@gmail.com");
            message.setTo(email);
            message.setSubject("Verify email");
            message.setText("http://localhost:8183/api/auth/verifyEmail?email=" + email + "&emailCode=" + emailCode);
            javaMailSender.send(message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
