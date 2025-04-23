package yool1.ma.authservice.web;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yool1.ma.authservice.dto.CertificationDTO;
import yool1.ma.authservice.entities.Certification;
import yool1.ma.authservice.repository.CertificationRepository;
import yool1.ma.authservice.services.CertificationService;

import java.util.List;

@RestController
@CrossOrigin("*")
public class CertificationRestController {

    private final CertificationRepository certificationRepository;
    private final CertificationService certificationService;

    public CertificationRestController(CertificationRepository certificationRepository, CertificationService certificationService) {
        this.certificationRepository = certificationRepository;
        this.certificationService = certificationService;
    }

    //---------------------List Certification --------------------
    @GetMapping(path = "AllCertifications")
    public List<Certification> getAllCertifications() {
        return certificationRepository.findAll();
    }


    //---------------------List Experience By userId --------------------
    @GetMapping("/AllCertificationsByUserId/{userId}")
    public List<Certification> getCertificationsByUserId(@PathVariable Long userId) {
        return certificationRepository.findByUserId(userId);
    }


    @PostMapping("/AddNewCertification")
    public ResponseEntity<Certification> createCertification(@RequestBody CertificationDTO dto) {
        Certification createCertification = certificationService.createCertification(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createCertification);
    }



    @DeleteMapping("/DeleteCertification/{id}")
    public ResponseEntity<Void> deleteCertification(@PathVariable Long id) {
        certificationService.deleteCertification(id);
        return ResponseEntity.noContent().build();
    }


}
