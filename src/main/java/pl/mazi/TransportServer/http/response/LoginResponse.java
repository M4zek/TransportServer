package pl.mazi.TransportServer.http.response;

import lombok.Data;

import java.util.List;

@Data
public class LoginResponse {
    private Long id;
    private String jwtToken;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String placeOfResidence;
    private List<String> roles;

    public LoginResponse(String jwtToken, Long id, String username, String email, String firstName, String lastName, String phoneNumber, String placeOfResidence ,List<String> roles) {
        this.id = id;
        this.jwtToken = jwtToken;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.placeOfResidence = placeOfResidence;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
    }
}
