package uz.uzkassa.uzkassa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
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
import uz.uzkassa.uzkassa.entity.Company;
import uz.uzkassa.uzkassa.entity.Employee;
import uz.uzkassa.uzkassa.entity.enums.SystemRoleName;
import uz.uzkassa.uzkassa.repository.EmployeeRepository;
import uz.uzkassa.uzkassa.security.JwtProvider;
import uz.uzkassa.uzkassa.service.AuthService;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {

    private EmployeeRepository employeeRepository;
    private JavaMailSender javaMailSender;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;
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
            return new ResponseDto("This username already exist",false);
        }
         // employee information gathering
        Employee employee=new Employee();
        employee.setFirstName(signUpDto.getFirstName());
        employee.setLastName(signUpDto.getLastName());
        employee.setUsername(signUpDto.getUsername());
        employee.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        // company information gathering
        Company company =new Company(signUpDto.getCompanyName(),signUpDto.getCompanyAddress(),signUpDto.getCompanyZipCode());

        employee.setCompany(company);
        employee.setRoleName(SystemRoleName.SYSTEM_ROLE_ADMIN);
        String emailCode=String.valueOf(new Random().nextInt(99999999)).substring(4);
        employee.setEmailCode(emailCode);
        employeeRepository.save(employee);
        sendEmail(signUpDto.getUsername(),emailCode);
        return new ResponseDto("User successfully saved",true);
    }

@Override
    public ResponseDto verifyEmail(String email, String emailCode) {
        Optional<Employee> optionalEmployee = employeeRepository.findByUsernameAndEmailCode(email, emailCode);
        if (!optionalEmployee.isPresent()) {
            return new ResponseDto("Employee with this email and email code not found",false)  ;
        }
        if (optionalEmployee.get().getEmailCode()==null) {
            return new ResponseDto("This email already verified",false);
        }
        Employee employee=optionalEmployee.get();
        employee.setEmailCode(null);
        employee.setEnabled(true);
        employeeRepository.save(employee);
        return new ResponseDto("Email successfully verified.",true);
    }

    @Override
    public ResponseDto signIn(SignIn signIn) {
        if (!employeeRepository.findByUsername(signIn.getUsername()).isPresent()) {
            return new ResponseDto("Employee not found",false);
        }
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signIn.getUsername(), signIn.getPassword()));
            Employee employee = (Employee) authentication.getPrincipal();
            String token = jwtProvider.generateJwt(employee.getUsername());
            return new ResponseDto("You successfully logged in",true,token);
        }catch (Exception e){
            return new ResponseDto("Password  is incorrect",false);

        }

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return employeeRepository.findByUsername(username).orElseThrow();
    }



    public Boolean sendEmail(String email,String emailCode){
        try{
            SimpleMailMessage message=new SimpleMailMessage();
            message.setFrom("shukhrat1201@gmail.com");
            message.setTo(email);
            message.setSubject("Verify email");
            message.setText("http://localhost:8183/api/auth/verifyEmail?email=" + email + "&emailCode=" + emailCode);
            javaMailSender.send(message);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  false;
    }

}
