package yool1.ma.authservice.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;
import yool1.ma.authservice.dto.DocumentDto;
import yool1.ma.authservice.dto.ExperienceDto;
import yool1.ma.authservice.entities.Document;
import yool1.ma.authservice.entities.Experience;
import yool1.ma.authservice.entities.User;
import yool1.ma.authservice.repository.ExperienceRepository;
import yool1.ma.authservice.repository.UserRepository;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class ExperienceService {

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private UserRepository userRepository;


    //---------------------Add Experience --------------------
    public Experience addExperienceToUser(ExperienceDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Experience exp = new Experience();
        exp.setPost(dto.getPost());
        exp.setTypeEmploi(dto.getTypeEmploi());
        exp.setEntreprise(dto.getEntreprise());
        exp.setDateDebut(dto.getDateDebut());
        exp.setDateFin(dto.getDateFin());
        exp.setVille(dto.getVille());
        exp.setDescription(dto.getDescription());
        exp.setUser(user);

        return experienceRepository.save(exp);
    }

    //---------------------Delete Experience --------------------
    public void deleteExperienceById(Long id) {
        if (!experienceRepository.existsById(id)) {
            throw new EntityNotFoundException("Experience not found with id: " + id);
        }
        experienceRepository.deleteById(id);
    }


}



