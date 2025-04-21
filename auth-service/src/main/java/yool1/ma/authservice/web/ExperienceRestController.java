package yool1.ma.authservice.web;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yool1.ma.authservice.dto.ExperienceDto;
import yool1.ma.authservice.entities.Experience;
import yool1.ma.authservice.entities.User;
import yool1.ma.authservice.repository.ExperienceRepository;
import yool1.ma.authservice.repository.UserRepository;
import yool1.ma.authservice.services.ExperienceService;

import java.util.List;

@RestController
@CrossOrigin("*")
public class ExperienceRestController {

    @Autowired
    private ExperienceService experienceService;
    @Autowired
    private ExperienceRepository experienceRepository;


    @GetMapping(path = "AllExperiences")
    public List<Experience> getAllExperiences() {
        return experienceRepository.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<Experience> getExperiencesByUserId(@PathVariable Long userId) {
        return experienceRepository.findByUserId(userId);
    }

    @PostMapping(path = "/AddNewExperience")
    public ResponseEntity<Experience> addExperience(@RequestBody ExperienceDto dto) {
        Experience saved = experienceService.addExperienceToUser(dto);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/deleteExperience/{id}")
    public ResponseEntity<String> deleteExperience(@PathVariable Long id) {
        try {
            experienceService.deleteExperienceById(id);
            return ResponseEntity.ok("Experience deleted successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Experience not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the experience.");
        }
    }


}



