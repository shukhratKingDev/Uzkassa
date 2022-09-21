package uz.uzkassa.uzkassa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;
    private String companyAddress;
    private String companyZipCode;
    private boolean blocked = false;//  initially company not blocked

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id")
    private Set<Employee> employees = new HashSet<>();

    public Company(String companyName, String companyAddress, String companyZipCode) {
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.companyZipCode = companyZipCode;
    }
}

