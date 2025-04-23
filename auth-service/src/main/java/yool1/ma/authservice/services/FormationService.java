package yool1.ma.authservice.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import yool1.ma.authservice.dto.FormationDTO;
import yool1.ma.authservice.entities.Formation;
import yool1.ma.authservice.entities.User;
import yool1.ma.authservice.repository.FormationRepository;
import yool1.ma.authservice.repository.UserRepository;


@Service
@Transactional
public class FormationService {

    private final FormationRepository formationRepository;
    private final UserRepository userRepository;

    public FormationService(FormationRepository formationRepository, UserRepository userRepository) {
        this.formationRepository = formationRepository;
        this.userRepository = userRepository;
    }

    // Create a formation for a specific user
    public Formation createFormation(FormationDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Formation formation = new Formation();
        formation.setEcole(dto.getEcole());
        formation.setDiplome(dto.getDiplome());
        formation.setDomain_etude(dto.getDomainEtude());
        formation.setDateDebut(dto.getDateDebut());
        formation.setDateFin(dto.getDateFin());
        formation.setDescription(dto.getDescription());
        formation.setUser(user);

        return formationRepository.save(formation);
    }

    // Delete a formation (ensuring it belongs to the user)
    public void deleteFormation(Long id) {
        if (!formationRepository.existsById(id)) {
            throw new EntityNotFoundException("Experience not found with id: " + id);
        }
        formationRepository.deleteById(id);
    }
}

