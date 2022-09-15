package uz.uzkassa.uzkassa;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import uz.uzkassa.uzkassa.controller.AuthController;
import uz.uzkassa.uzkassa.controller.EmployeeController;
import uz.uzkassa.uzkassa.dto.EmployeeDto;
import uz.uzkassa.uzkassa.dto.ResponseDto;
import uz.uzkassa.uzkassa.dto.SignIn;
import uz.uzkassa.uzkassa.dto.SignUpDto;
import uz.uzkassa.uzkassa.entity.Company;
import uz.uzkassa.uzkassa.entity.Employee;
import uz.uzkassa.uzkassa.entity.enums.SystemRoleName;
import uz.uzkassa.uzkassa.service.AuthService;
import uz.uzkassa.uzkassa.service.EmployeeService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthController.class)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
@MockBean
    private AuthService authService;
@MockBean
private ObjectMapper objectMapper;

@Test
    public void signUp() throws Exception {
    SignUpDto signUpDto=new SignUpDto("shukhratjon","rayimjonov","7814721s@gmail.com","12012002",
            "Uzkassa","Tashkent","100256");
    Mockito.when(authService.signUp(signUpDto)).thenReturn(new ResponseDto("Employee successfully saved",true));
String url="/api/auth/signUp";
ResponseDto responseDto=new ResponseDto("Employee successfully saved",true);
MvcResult mvcResult=mockMvc.perform(get(url)).andExpect(status().isCreated()).andReturn();
String actualJsonResponse=mvcResult.getResponse().getContentAsString();
String expectedJsonResponse=objectMapper.writeValueAsString(responseDto);
assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);

}



@Test
    public void verifyEmail() throws Exception {

    String emailCode="1236";
    String email="shukhrat1201@gmail.com";
    Mockito.when(authService.verifyEmail(email,emailCode)).thenReturn(new ResponseDto("Email successfully verified",true));
    String url="/api/auth/verify";
ResponseDto responseDto=new ResponseDto("Email successfully verified",true);
    MvcResult mvcResult=mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
    String actualJsonResponse=mvcResult.getResponse().getContentAsString();
    String expectedJsonResponse=objectMapper.writeValueAsString(responseDto);
    assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);

}


    @Test
    public void signIn() throws Exception {
        SignIn signIn=new SignIn("shukhrat1201@gmail.com","12012002");
        ResponseDto responseDto=new ResponseDto("Employee information successfully modified",false);
        Mockito.when(authService.signIn(signIn));
        String url="/api/auth/signIn";

        MvcResult mvcResult=mockMvc.perform(post(url)).andExpect(status().isOk()).andReturn();
        String actualJsonResponse=mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse=objectMapper.writeValueAsString(responseDto);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);

    }

}
