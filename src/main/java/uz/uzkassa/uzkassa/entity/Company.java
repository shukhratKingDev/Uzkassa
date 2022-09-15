package uz.uzkassa.uzkassa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Company {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String companyName;
    private String companyAddress;
    private String companyZipCode;
    private boolean blocked=false;//  initially company not blocked
    @OneToMany(mappedBy = "company",fetch = FetchType.LAZY)
    private List<Employee> employees;


    public Company(String companyName, String companyAddress, String companyZipCode) {
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.companyZipCode = companyZipCode;
    }
}

