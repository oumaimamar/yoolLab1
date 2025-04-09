package yool1.ma.authservice.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yool1.ma.authservice.dto.UserDTO;
import yool1.ma.authservice.entities.User;
import yool1.ma.authservice.repository.ApprenantRepository;
import yool1.ma.authservice.repository.UserRepository;
import yool1.ma.authservice.services.RegisterService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin("*")
public class RegisterRestController {

    public UserRepository userRepository;
    public ApprenantRepository apprenantRepository;
    public RegisterService registerService;


    public RegisterRestController(ApprenantRepository apprenantRepository, UserRepository userRepository, RegisterService registerService) {
        this.apprenantRepository = apprenantRepository;
        this.userRepository = userRepository;
        this.registerService = registerService;
    }


    @PostMapping(path = "/newUser")
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setVille(userDTO.getVille());
        user.setMotDePasse(userDTO.getMotDePasse());

        // Set default values
        user.setDateInscription(LocalDate.now());
        user.setRole("APPRENANT");  // Default role

        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

}
