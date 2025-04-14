package yool1.ma.authservice.web;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yool1.ma.authservice.dto.ProfileUpdateDTO;
import yool1.ma.authservice.dto.UserProfileDTO;
import yool1.ma.authservice.entities.Profile;
import yool1.ma.authservice.entities.User;
import yool1.ma.authservice.mapper.UserMapper;
import yool1.ma.authservice.repository.ApprenantRepository;
import yool1.ma.authservice.repository.ProfileRepository;
import yool1.ma.authservice.repository.UserRepository;
import yool1.ma.authservice.services.ProfileService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
public class ProfileRestController {


    private final UserMapper userMapper;

    public UserRepository userRepository;
    public ApprenantRepository apprenantRepository;
    public ProfileRepository profileRepository;
    public ProfileService profileService;


    public ProfileRestController(UserMapper userMapper, ApprenantRepository apprenantRepository, UserRepository userRepository,
                                 ProfileRepository profileRepository,ProfileService profileService) {
        this.userMapper = userMapper;
        this.apprenantRepository = apprenantRepository;
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.profileService = profileService;
    }


    @GetMapping(path = "/users")
    public List<User> AllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/userProfiles")
    public ResponseEntity<List<UserProfileDTO>> getAllUsersWithProfiles() {
        List<UserProfileDTO> userProfiles = userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userProfiles);
    }

    // Get user by ID with profile -------------------------------------------
    @GetMapping("/userProfiles/{userId}")
    public ResponseEntity<UserProfileDTO> getUserWithProfile(@PathVariable Long userId) {
        return userRepository.findById(userId)
                .map(userMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


//    @GetMapping("/{userId}")
//    public ResponseEntity<UserProfileDTO> getUserWithProfile(@PathVariable Long userId) {
//        return userRepository.findById(userId)
//                .map(user -> {
//                    UserProfileDTO dto = new UserProfileDTO();
//                    // Map User to DTO
//                    dto.setUserId(user.getUserId());
//                    dto.setFirstName(user.getFirstName());
//                    dto.setLastName(user.getLastName());
//                    dto.setEmail(user.getEmail());
//                    dto.setPhone(user.getPhone());
//                    dto.setVille(user.getVille());
//                    dto.setRole(user.getRole());
//
//                    // Map Profile to DTO if exists
//                    if (user.getProfile() != null) {
//                        dto.setHeadline(user.getProfile().getHeadline());
//                        dto.setBio(user.getProfile().getBio());
//                        dto.setPhotoUrl(user.getProfile().getPhotoUrl());
//                        dto.setLocation(user.getProfile().getLocation());
//                    }
//
//                    return ResponseEntity.ok(dto);
//                })
//                .orElse(ResponseEntity.notFound().build());
//    }


    // Update user profile -----------------------------------------------------
    @PutMapping("userAddProfile/{userId}/profile")
    @Transactional
    public ResponseEntity<Profile> updateUserProfile(
            @PathVariable Long userId,
            @RequestBody ProfileUpdateDTO profileUpdateDTO) {
        //return this.profileService.updateUserProfile(userId, profileUpdateDTO);
        //OR

        try {
            Profile updatedProfile = profileService.updateUserProfile(userId, profileUpdateDTO);
            return ResponseEntity.ok(updatedProfile);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }




}
