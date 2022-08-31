package pl.mazi.TransportServer.http.request;

import lombok.Data;
import pl.mazi.TransportServer.entity.ERole;

@Data
public class LoginRequest {

    private String username;
    private String password;
    private ERole role;

}
