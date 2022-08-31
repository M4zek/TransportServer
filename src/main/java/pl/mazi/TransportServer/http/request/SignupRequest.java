package pl.mazi.TransportServer.http.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class SignupRequest {
    private String username;
    private String email;
    private String password;
    private Set<String> role;


    private String peselNumber;
    private String placeOfResidence;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private LocalDate hireDate;
    private LocalDate birthDate;
    private String employeType;
    private Double salary;
}
