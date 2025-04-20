package yool1.ma.authservice.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import yool1.ma.authservice.entities.Experience;
import yool1.ma.authservice.entities.User;
import yool1.ma.authservice.repository.ExperienceRepository;

@Service
@Transactional
public class ExperienceService {

    private ExperienceRepository experienceRepository;

    public ExperienceService(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

}
