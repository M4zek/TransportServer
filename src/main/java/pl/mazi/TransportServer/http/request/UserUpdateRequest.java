package pl.mazi.TransportServer.http.request;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private Long idUser;
    private String username;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String placeOfResidence;
    private String password;
}
