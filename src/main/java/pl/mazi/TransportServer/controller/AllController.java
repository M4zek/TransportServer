package pl.mazi.TransportServer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mazi.TransportServer.http.response.MessageResponse;
import pl.mazi.TransportServer.repo.UserRepository;
import pl.mazi.TransportServer.security.services.UserServices;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/all/user")
@RequiredArgsConstructor
public class AllController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServices userServices;


    @PostMapping("/resetDate")
    public ResponseEntity<?> resetUserDate(@Valid @RequestBody String email){
        if(!userRepository.existsByEmail(email)){
            return ResponseEntity.ok().body(new MessageResponse("Email nie istnieje"));
        }
        // TODO MAKE A EMAIL SENDER WITH NEW USERNAME AND PASSWORD
//        String newUserName = userServices.makeNewUserName();
//        String newPassword = userServices.makeNewPassword();
//        user.setUsername(newUserName);
//        user.setPassword(newPassword);
//        userRepository.save(user);
        return ResponseEntity.ok().body(new MessageResponse("Nowe dane logowania zostały przesłane na email: " + email));
    }
}
