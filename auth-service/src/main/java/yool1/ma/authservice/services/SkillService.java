package yool1.ma.authservice.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import yool1.ma.authservice.dto.FormationDTO;
import yool1.ma.authservice.dto.SkillDTO;
import yool1.ma.authservice.entities.Formation;
import yool1.ma.authservice.entities.Skill;
import yool1.ma.authservice.entities.User;
import yool1.ma.authservice.repository.FormationRepository;
import yool1.ma.authservice.repository.SkillRepository;
import yool1.ma.authservice.repository.UserRepository;

@Service
@Transactional
public class SkillService {
    private final SkillRepository skillRepository;
    private final UserRepository userRepository;

    public SkillService(SkillRepository skillRepository, UserRepository userRepository) {
        this.skillRepository = skillRepository;
        this.userRepository = userRepository;
    }

    // Create a Skill for a specific user
    public Skill createSkill(SkillDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Skill skill = new Skill();
        skill.setName(dto.getName());

        skill.setUser(user);

        return skillRepository.save(skill);
    }

    // Delete a Skill (ensuring it belongs to the user)
    public void deleteSkill(Long id) {
        if (!skillRepository.existsById(id)) {
            throw new EntityNotFoundException("Skill not found with id: " + id);
        }
        skillRepository.deleteById(id);
    }
}

