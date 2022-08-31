package pl.mazi.TransportServer.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Email
    @Column
    private String email;

    @Column
    private String password;

    public User(){}

    public User(String email , String username, String encode) {
        this.email = email;
        this.password = encode;
        this.username = username;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idEmployee")
    private Employee employee;



    // METHOD

    public void update(String newEmail, String newUsername, String newPassword){
        System.out.println("NEW PASS: " + newPassword);
        System.out.println("NEW EMAIL:" + newEmail);
        System.out.println("NEW USERNAME: " + newUsername);

        if(!newUsername.isEmpty()) this.username = newUsername;
        if(!newEmail.isEmpty()) this.email = newEmail;
        if(!newPassword.isEmpty()) this.password = newPassword;
    }
}