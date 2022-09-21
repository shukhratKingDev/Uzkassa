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
import uz.uzkassa.uzkassa.controller.EmployeeController;
import uz.uzkassa.uzkassa.dto.EmployeeDto;
import uz.uzkassa.uzkassa.dto.ResponseDto;
import uz.uzkassa.uzkassa.entity.Company;
import uz.uzkassa.uzkassa.entity.Employee;
import uz.uzkassa.uzkassa.enums.SystemRoleName;
import uz.uzkassa.uzkassa.service.EmployeeService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
@MockBean
    private EmployeeService employeeService;
@MockBean
private ObjectMapper objectMapper;

@Test
    public void getAllEmployeesTest() throws Exception {
    List<Employee>list=new ArrayList<>();
    list.add(new Employee(1L,"shukhratjon","rayimjonov","7814721s@gmail.com","12012002",true,"1234", SystemRoleName.SYSTEM_ROLE_ADMIN,1L,new Company(
            "Uzkassa","Tashkent","100256"

    )));list.add(new Employee(1L,"shukhratjon","rayimjonov","7814721s@gmail.com","12012002",true,"1234", SystemRoleName.SYSTEM_ROLE_ADMIN,1L,new Company(
            "Uzkassa","Tashkent","100256"

    )));
    Mockito.when(employeeService.getAll()).thenReturn(list);
String url="/api/employee/all";
MvcResult mvcResult=mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
String actualJsonResponse=mvcResult.getResponse().getContentAsString();
String expectedJsonResponse=objectMapper.writeValueAsString(list);
assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);

}



@Test
    public void getEmployeeById() throws Exception {
    Employee employee=new Employee(1L,"shukhratjon","rayimjonov","7814721s@gmail.com","12012002",true,"1234", SystemRoleName.SYSTEM_ROLE_ADMIN,1L,new Company(
            "Uzkassa","Tashkent","100256"

    ));
    Employee savedEmployee=new Employee(1L,"shukhratjon","rayimjonov","7814721s@gmail.com","12012002",true,"1234", SystemRoleName.SYSTEM_ROLE_ADMIN,1L,new Company(
            "Uzkassa","Tashkent","100256"

    ));
    Long id=1L;
    Mockito.when(employeeService.get(id)).thenReturn(savedEmployee);
    String url="/api/employee/"+id;

    MvcResult mvcResult=mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
    String actualJsonResponse=mvcResult.getResponse().getContentAsString();
    String expectedJsonResponse=objectMapper.writeValueAsString(employee);
    assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);

}


    @Test
    public void editEmployeeTest() throws Exception {
        Employee employee=new Employee(1L,"shukhratjon","rayimjonov","7814721s@gmail.com","12012002",true,"1234", SystemRoleName.SYSTEM_ROLE_ADMIN,1L,new Company(
                "Uzkassa","Tashkent","100256"

        ));

       EmployeeDto employeeDto=new EmployeeDto("shukhratjon","rayimjonov","7814721s@gmail.com",true);

        Long id=1L;
        ResponseDto responseDto=new ResponseDto("Employee information successfully modified",false);
        Mockito.when(employeeService.update(id,employeeDto)).thenReturn(responseDto);
        String url="/api/employee/"+id;

        MvcResult mvcResult=mockMvc.perform(put(url)).andExpect(status().isOk()).andReturn();
        String actualJsonResponse=mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse=objectMapper.writeValueAsString(responseDto);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);

    }

@Test
    public void addEmployee() throws Exception {
    EmployeeDto employeeDto=new EmployeeDto(
            "shukhratjon","Rayimjonov","shukhrat_developer",true

    );
    String url="/api/employee/add";
    ResponseDto responseDto=new ResponseDto("Employee successfully saved",true);
    Mockito.when(employeeService.add(employeeDto)).thenReturn(responseDto);
    mockMvc.perform(post(url).contentType("application/json")
                    .with(csrf())
            .content(objectMapper.writeValueAsString(responseDto)))
            .andExpect(status().isOk());

}


    @Test
    public void deleteEmployee() throws Exception {


        Long id=1L;
        Mockito.when(employeeService.delete(id)).thenReturn(new ResponseDto("Employee successfully deleted",true));
        String url="/api/employee/"+id;
ResponseDto responseDto=new ResponseDto("Employee successfully deleted",true);
        MvcResult mvcResult=mockMvc.perform(delete(url)).andExpect(status().isOk()).andReturn();
        String actualJsonResponse=mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse=objectMapper.writeValueAsString(responseDto);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }
}
