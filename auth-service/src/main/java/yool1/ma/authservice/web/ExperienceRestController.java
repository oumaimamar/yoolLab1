package yool1.ma.authservice.web;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import yool1.ma.authservice.entities.Experience;
import yool1.ma.authservice.entities.User;
import yool1.ma.authservice.repository.ExperienceRepository;
import yool1.ma.authservice.services.ExperienceService;

@RestController
@CrossOrigin("*")
public class ExperienceRestController {

    private ExperienceService experienceService;

    public ExperienceRestController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }


}
