package yool1.ma.authservice.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yool1.ma.authservice.dto.FormationDTO;
import yool1.ma.authservice.dto.SkillDTO;
import yool1.ma.authservice.entities.Formation;
import yool1.ma.authservice.entities.Skill;
import yool1.ma.authservice.repository.FormationRepository;
import yool1.ma.authservice.repository.SkillRepository;
import yool1.ma.authservice.services.FormationService;
import yool1.ma.authservice.services.SkillService;

import java.util.List;

@RestController
@CrossOrigin("*")
public class SkillRestController {

    private final SkillService skillService;
    private final SkillRepository skillRepository;

    public SkillRestController(SkillService skillService, SkillRepository skillRepository) {
        this.skillService = skillService;
        this.skillRepository = skillRepository;
    }


    //---------------------List Experience --------------------
    @GetMapping(path = "AllSkills")
    public List<Skill> getAllSkill() {
        return skillRepository.findAll();
    }


    //---------------------List Experience By userId --------------------
    @GetMapping("/AllSkillsByUserId/{userId}")
    public List<Skill> getSkillsByUserId(@PathVariable Long userId) {
        return skillRepository.findByUserId(userId);
    }


    @PostMapping("/AddNewSkill")
    public ResponseEntity<Skill> createSkill(@RequestBody SkillDTO dto) {
        Skill createdSkill = skillService.createSkill(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSkill);
    }


    @DeleteMapping("/DeleteSkill/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
        return ResponseEntity.noContent().build();
    }


}
