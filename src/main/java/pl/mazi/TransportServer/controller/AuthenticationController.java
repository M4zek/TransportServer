package pl.mazi.TransportServer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.mazi.TransportServer.http.request.LoginRequest;
import pl.mazi.TransportServer.http.response.LoginResponse;
import pl.mazi.TransportServer.http.response.MessageResponse;
import pl.mazi.TransportServer.repo.UserRepository;
import pl.mazi.TransportServer.security.jwt.TokenManager;
import pl.mazi.TransportServer.security.services.IUserDetails;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenManager tokenManager;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenManager.generateJwtToken(authentication);

        IUserDetails userDetails = (IUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        if(!loginRequest.getRole().toString().equals(roles.get(0))){
            return ResponseEntity.badRequest().body(new MessageResponse("Nie posiadasz odpowiednich uprawnień do korzystania z systemu!"));
        } else {
            LoginResponse loginResponse = new LoginResponse(
                    jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    userDetails.getFirstName(),
                    userDetails.getLastName(),
                    userDetails.getPhoneNumber(),
                    userDetails.getPlaceOfResidence(),
                    roles);
            return ResponseEntity.ok(loginResponse);
        }
    }
}