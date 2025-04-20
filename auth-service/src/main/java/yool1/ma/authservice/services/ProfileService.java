package yool1.ma.authservice.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import yool1.ma.authservice.dto.ProfileUpdateDTO;
import yool1.ma.authservice.entities.Profile;
import yool1.ma.authservice.repository.ProfileRepository;
import yool1.ma.authservice.repository.UserRepository;

@Service
@Transactional
public class ProfileService {

    public UserRepository userRepository;
    public ProfileRepository profileRepository;

    public ProfileService(UserRepository userRepository, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

 //-----------------------AddProfileToUser--------------------------
    public Profile updateUserProfile(Long userId, ProfileUpdateDTO profileUpdateDTO) {
        return userRepository.findById(userId)
                .map(user -> {
                    Profile profile = user.getProfile();
                    if (profile == null) {
                        profile = new Profile();
                        profile.setUser(user);
                        user.setProfile(profile);
                    }
                    if (profileUpdateDTO.getHeadline() != null) {
                        profile.setHeadline(profileUpdateDTO.getHeadline());
                    }
                    if (profileUpdateDTO.getBio() != null) {
                        profile.setBio(profileUpdateDTO.getBio());
                    }
                    if (profileUpdateDTO.getPhotoUrl() != null) {
                        profile.setPhotoUrl(profileUpdateDTO.getPhotoUrl());
                    }
                    if (profileUpdateDTO.getLocation() != null) {
                        profile.setLocation(profileUpdateDTO.getLocation());
                    }

                    return profileRepository.save(profile);
                })
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    }



}
