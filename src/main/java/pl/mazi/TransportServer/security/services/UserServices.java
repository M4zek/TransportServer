package pl.mazi.TransportServer.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserServices {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String encodePassword(String pass){
        return passwordEncoder.encode(pass);
    }
    public Boolean passwordValidation(String newPassword, String oldPassword) {
        return passwordEncoder.matches(newPassword,oldPassword);
    }

    public String makeNewPassword(){
        Random random = new Random();
        String password = random.ints(97,123)
                .limit(15)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        System.out.println("\n\nNEW PASSWORD: " + password + "\n\n");
        return passwordEncoder.encode(password);
    }

    public String makeNewUserName(){
        Random random = new Random();
        String username =  random.ints(97,123)
                .limit(8)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return username;
    }

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
}
