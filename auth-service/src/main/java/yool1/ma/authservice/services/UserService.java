package yool1.ma.authservice.services;

import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import yool1.ma.authservice.dto.UserDTO;
import yool1.ma.authservice.entities.User;
import yool1.ma.authservice.repository.UserRepository;

import java.time.LocalDate;

@Service
@Transactional
public class UserService {

    public UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //-------------AddNewUser----------------------------------
    public ResponseEntity<User> addNewUser(UserDTO userDTO) {
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

    //-------------DeleteUser----------------------------------
    public ResponseEntity<Object> deleteUser(Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
