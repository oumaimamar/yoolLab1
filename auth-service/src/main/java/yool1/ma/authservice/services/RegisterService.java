package yool1.ma.authservice.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yool1.ma.authservice.entities.User;
import yool1.ma.authservice.repository.ApprenantRepository;
import yool1.ma.authservice.repository.UserRepository;

@Service
@Transactional
public class RegisterService {

    public UserRepository userRepository;
    public ApprenantRepository apprenantRepository;


    public RegisterService (ApprenantRepository apprenantRepository, UserRepository userRepository) {
        this.apprenantRepository = apprenantRepository;
        this.userRepository = userRepository;
    }

}


