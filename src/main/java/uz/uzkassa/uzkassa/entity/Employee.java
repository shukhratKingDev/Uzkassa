package uz.uzkassa.uzkassa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.uzkassa.uzkassa.entity.enums.SystemRoleName;

import javax.persistence.*;

import java.util.Collection;
import java.util.Collections;

import static org.hibernate.annotations.OnDeleteAction.*;


@Data
@AllArgsConstructor
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
    @Column(nullable = false,unique = true)
    private String username;// username is email of employee
    private String password;
    private boolean enabled;
    private String emailCode;
    @Enumerated(EnumType.STRING)
    private SystemRoleName roleName;
    @OnDelete(action = CASCADE)
    @ManyToOne(optional = false,fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private Company company;


    public Employee(String firstName, String lastName, String username, SystemRoleName roleName,Company company) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.roleName=roleName;
        this.company=company;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority(this.roleName.name());
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
