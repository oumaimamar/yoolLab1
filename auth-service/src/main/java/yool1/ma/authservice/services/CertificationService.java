package yool1.ma.authservice.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import yool1.ma.authservice.dto.CertificationDTO;
import yool1.ma.authservice.dto.FormationDTO;
import yool1.ma.authservice.entities.Certification;
import yool1.ma.authservice.entities.Formation;
import yool1.ma.authservice.entities.User;
import yool1.ma.authservice.repository.CertificationRepository;
import yool1.ma.authservice.repository.FormationRepository;
import yool1.ma.authservice.repository.UserRepository;

@Service
@Transactional
public class CertificationService {

    private final CertificationRepository certificationRepository;
    private final UserRepository userRepository;

    public CertificationService(CertificationRepository certificationRepository, UserRepository userRepository) {
        this.certificationRepository = certificationRepository;
        this.userRepository = userRepository;
    }


    // Create a formation for a specific user
    public Certification createCertification(CertificationDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Certification certification = new Certification();
        certification.setTitre(dto.getTitre());
        certification.setOrganisation(dto.getOrganisation());
        certification.setDateDebut(dto.getDateDebut());
        certification.setDateFin(dto.getDateFin());
        certification.setDescription(dto.getDescription());
        certification.setUser(user);

        return certificationRepository.save(certification);
    }


    // Delete a certification (ensuring it belongs to the user)
    public void deleteCertification(Long id) {
        if (!certificationRepository.existsById(id)) {
            throw new EntityNotFoundException("Experience not found with id: " + id);
        }
        certificationRepository.deleteById(id);
    }

}
