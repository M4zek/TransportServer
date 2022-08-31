package pl.mazi.TransportServer.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.mazi.TransportServer.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
public class IUserDetails implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String email;

    @JsonIgnore
    private String password;
    private String firstName;
    private String lastName;

    private String placeOfResidence;
    private String phoneNumber;
    private long idEmployee;



    private Collection<? extends GrantedAuthority> authorities;

    public IUserDetails(Long id, String email, String username, String password, String firstName, String lastName, Long idEmployee, String placeOfResidence, String phoneNumber ,Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.authorities = authorities;
        this.firstName = firstName;
        this.lastName = lastName;
        this.idEmployee = idEmployee;
        this.phoneNumber = phoneNumber;
        this.placeOfResidence = placeOfResidence;
    }

    public static IUserDetails build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new IUserDetails(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getPassword(),
                user.getEmployee().getFirstName(),
                user.getEmployee().getLastName(),
                user.getEmployee().getIdEmployee(),
                user.getEmployee().getPlaceOfResidence(),
                user.getEmployee().getPhoneNumber(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

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

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        IUserDetails user = (IUserDetails) o;
        return Objects.equals(id, user.id);
    }
}

