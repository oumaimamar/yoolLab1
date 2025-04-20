package yool1.ma.authservice.web;

import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yool1.ma.authservice.dto.UserDTO;
import yool1.ma.authservice.entities.User;
import yool1.ma.authservice.repository.UserRepository;
import yool1.ma.authservice.services.UserService;

import java.util.List;

@RestController
@CrossOrigin("*")
public class UserRestController {

    public UserRepository userRepository;
    public UserService userService;


    public UserRestController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }



    @GetMapping(path = "/users")
    public List<User> AllUsers() {
        return userRepository.findAll();
    }


    @PostMapping(path = "users/addUser")
    public ResponseEntity<User> addNewUser(@RequestBody UserDTO userDTO) {
       return userService.addNewUser(userDTO);
    }


    // Delete user and profile --------------------------------------------
    @DeleteMapping("users/deleteUser/{userId}")
    @Transactional
    public ResponseEntity<Object> deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }


}
