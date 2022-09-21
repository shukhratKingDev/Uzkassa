package uz.uzkassa.uzkassa.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.uzkassa.uzkassa.enums.SystemRoleName;

import javax.persistence.*;

import java.util.Collection;
import java.util.Collections;

import static org.hibernate.annotations.OnDeleteAction.*;


@Data
@NoArgsConstructor

@Entity
public class Employee implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String username;// username is email of employee
    private String password;
    private boolean enabled;
    private String emailCode;
    @Enumerated(EnumType.STRING)
    private SystemRoleName roleName;

    @Column(name = "company_id")
    Long companyId;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", updatable = false, insertable = false)
    Company company;

    public Employee(String firstName, String lastName, String username, SystemRoleName roleName, Company company) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.roleName = roleName;
        this.company = company;
    }

    public Employee(Long id, String firstName, String lastName, String username, String password, boolean enabled, String emailCode, SystemRoleName roleName, Long companyId, Company company) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.emailCode = emailCode;
        this.roleName = roleName;
        this.companyId = companyId;
        this.company = company;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(this.roleName.name());
        return Collections.singleton(simpleGrantedAuthority);
    }


    // those fields are not necessary for this application
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


}
