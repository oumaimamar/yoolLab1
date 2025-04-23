package yool1.ma.authservice.web;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yool1.ma.authservice.dto.FormationDTO;
import yool1.ma.authservice.entities.Experience;
import yool1.ma.authservice.entities.Formation;
import yool1.ma.authservice.repository.FormationRepository;
import yool1.ma.authservice.services.FormationService;

import java.util.List;

@RestController
@CrossOrigin("*")
public class FormationRestController {

    private final FormationService formationService;
    private final FormationRepository formationRepository;

    public FormationRestController(FormationService formationService, FormationRepository formationRepository) {
        this.formationService = formationService;
        this.formationRepository = formationRepository;
    }


    //---------------------List Experience --------------------
    @GetMapping(path = "AllFormations")
    public List<Formation> getAllFormation() {
        return formationRepository.findAll();
    }


    //---------------------List Experience By userId --------------------
    @GetMapping("/AllFormationsByUserId/{userId}")
    public List<Formation> getFormationsByUserId(@PathVariable Long userId) {
        return formationRepository.findByUserId(userId);
    }


    @PostMapping("/AddNewFormation")
    public ResponseEntity<Formation> createFormation(@RequestBody FormationDTO dto) {
        Formation createdFormation = formationService.createFormation(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFormation);
    }



    @DeleteMapping("/DeleteFormation/{id}")
    public ResponseEntity<Void> deleteFormation(@PathVariable Long id) {
        formationService.deleteFormation(id);
        return ResponseEntity.noContent().build();
    }
}

