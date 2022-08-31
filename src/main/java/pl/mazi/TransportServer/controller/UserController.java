package pl.mazi.TransportServer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mazi.TransportServer.entity.Employee;
import pl.mazi.TransportServer.entity.User;
import pl.mazi.TransportServer.http.request.UserUpdateRequest;
import pl.mazi.TransportServer.http.response.MessageResponse;
import pl.mazi.TransportServer.repo.UserRepository;
import pl.mazi.TransportServer.security.services.UserServices;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/admin/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServices userServices;

    @PostMapping("/update")
//    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest){

        System.out.println("\n\nAKTUALIZACJA UŻYTKOWNIKA\n\n");

        // Sprawdzanie czy istnieje użytkownik o podanym ID
        if(!userRepository.existsById(userUpdateRequest.getIdUser())){
            return ResponseEntity.ok().body(new MessageResponse("Nie ma takiego użytkownika"));
        }

        // Wyciągnięcie użytkownika z bazy danych
        User user = userRepository.getById(userUpdateRequest.getIdUser());

        // Sprawdzenie czy nowe hasło jest takie samo jak poprzednie
        if(userServices.passwordValidation(userUpdateRequest.getPassword(),user.getPassword())) {
            return ResponseEntity.ok().body(new MessageResponse("Nowe hasło nie może być takie samo jak poprzednie!"));
        }

        if(!user.getEmail().equals(userUpdateRequest.getEmail())){
            if(userRepository.existsByEmail(userUpdateRequest.getEmail())){
                return ResponseEntity.ok().body(new MessageResponse("Email jest już zajęty"));
            }
        }

        if(!user.getUsername().equals(userUpdateRequest.getUsername())){
            if(userRepository.existsByUsername(userUpdateRequest.getUsername())){
                return ResponseEntity.ok().body(new MessageResponse("Nazwa użytkownika jest zajęta"));
            }
        }

        String newPassword = "";
        if(!userUpdateRequest.getPassword().trim().isEmpty()){
            newPassword = userServices.encodePassword(userUpdateRequest.getPassword());
        }

        user.update(userUpdateRequest.getEmail(), userUpdateRequest.getUsername(), newPassword);

        Employee employee = user.getEmployee();
        employee.update(
                userUpdateRequest.getFirstName(),
                userUpdateRequest.getLastName(),
                userUpdateRequest.getPhoneNumber(),
                userUpdateRequest.getPlaceOfResidence()
        );

        user.setEmployee(employee);
        userRepository.save(user);
        return ResponseEntity.ok().body(new MessageResponse("UPDATE_SUCCES"));
    }
}