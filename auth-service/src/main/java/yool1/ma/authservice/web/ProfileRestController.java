package yool1.ma.authservice.web;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yool1.ma.authservice.dto.DocumentDto;
import yool1.ma.authservice.dto.ProfileUpdateDTO;
import yool1.ma.authservice.dto.UserProfileDTO;
import yool1.ma.authservice.entities.Document;
import yool1.ma.authservice.entities.Profile;
import yool1.ma.authservice.entities.User;
import yool1.ma.authservice.mapper.UserMapper;
import yool1.ma.authservice.repository.ApprenantRepository;
import yool1.ma.authservice.repository.DocumentRepository;
import yool1.ma.authservice.repository.ProfileRepository;
import yool1.ma.authservice.repository.UserRepository;
import yool1.ma.authservice.services.ProfileService;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
public class ProfileRestController {


    private final UserMapper userMapper;
    private final DocumentRepository documentRepository;

    public UserRepository userRepository;
    public ApprenantRepository apprenantRepository;
    public ProfileRepository profileRepository;
    public ProfileService profileService;


    public ProfileRestController(UserMapper userMapper, ApprenantRepository apprenantRepository, UserRepository userRepository,
                                 ProfileRepository profileRepository, ProfileService profileService, DocumentRepository documentRepository) {
        this.userMapper = userMapper;
        this.apprenantRepository = apprenantRepository;
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.profileService = profileService;
        this.documentRepository = documentRepository;
    }


    @GetMapping(path = "/users")
    public List<User> AllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/userProfiles")
    public ResponseEntity<List<UserProfileDTO>> getAllUsersWithProfiles() {
        List<UserProfileDTO> userProfiles = userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userProfiles);
    }

    // Get user by ID with profile -------------------------------------------
    @GetMapping("/userProfiles/{userId}")
    public ResponseEntity<UserProfileDTO> getUserWithProfile(@PathVariable Long userId) {
        return userRepository.findById(userId)
                .map(userMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


//    @GetMapping("/{userId}")
//    public ResponseEntity<UserProfileDTO> getUserWithProfile(@PathVariable Long userId) {
//        return userRepository.findById(userId)
//                .map(user -> {
//                    UserProfileDTO dto = new UserProfileDTO();
//                    // Map User to DTO
//                    dto.setUserId(user.getUserId());
//                    dto.setFirstName(user.getFirstName());
//                    dto.setLastName(user.getLastName());
//                    dto.setEmail(user.getEmail());
//                    dto.setPhone(user.getPhone());
//                    dto.setVille(user.getVille());
//                    dto.setRole(user.getRole());
//
//                    // Map Profile to DTO if exists
//                    if (user.getProfile() != null) {
//                        dto.setHeadline(user.getProfile().getHeadline());
//                        dto.setBio(user.getProfile().getBio());
//                        dto.setPhotoUrl(user.getProfile().getPhotoUrl());
//                        dto.setLocation(user.getProfile().getLocation());
//                    }
//
//                    return ResponseEntity.ok(dto);
//                })
//                .orElse(ResponseEntity.notFound().build());
//    }


    // Update user profile -----------------------------------------------------
    @PutMapping("userAddProfile/{userId}/profile")
    @Transactional
    public ResponseEntity<Profile> updateUserProfile(
            @PathVariable Long userId,
            @RequestBody ProfileUpdateDTO profileUpdateDTO) {
        //return this.profileService.updateUserProfile(userId, profileUpdateDTO);
        //OR

        try {
            Profile updatedProfile = profileService.updateUserProfile(userId, profileUpdateDTO);
            return ResponseEntity.ok(updatedProfile);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    //--------------------- DOCUMENT ---------------------------------------

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> addDocument(
            @Valid @ModelAttribute DocumentDto documentDto,
            BindingResult result) {

        Map<String, Object> response = new HashMap<>();

        // Image Required
        if (documentDto.getFilePath().isEmpty()) {
            result.addError(new FieldError("documentDto", "fileName", "The doc file is required"));
        }

        if (result.hasErrors()) {
            response.put("status", "error");
            response.put("errors", result.getAllErrors());
            return ResponseEntity.badRequest().body(response);
        }

        // Save the image file
        MultipartFile image = documentDto.getFilePath();
        Date dateDoc = new Date();
        String storageFileName = dateDoc.getTime() + ".." + image.getOriginalFilename();

        try {
            String uploadDir = "public/Doc/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception ex) {
            response.put("status", "error");
            response.put("message", "File upload failed");
            return ResponseEntity.internalServerError().body(response);
        }

        // Save Document in Database
        Document document = new Document();
        document.setTitre(documentDto.getTitre());
        document.setTypeDoc(documentDto.getTypeDoc());
        document.setDateAjout(dateDoc);
        document.setFileName(storageFileName);

        Document savedDocument = documentRepository.save(document);

        response.put("status", "success");
        response.put("message", "Document uploaded successfully");
        response.put("document", savedDocument);

        return ResponseEntity.ok(response);
    }


    @GetMapping(path = "/allDocs")
    public List<Document> AllDocuments() {
        return documentRepository.findAll();
    }


    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long id) {
        try {
            Document document = documentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Document not found with id: " + id));

            // Get the file path
            Path filePath = Paths.get("public/Doc/" + document.getFileName()).toAbsolutePath().normalize();

            // Check if file exists
            if (!Files.exists(filePath)) {
                throw new RuntimeException("File not found: " + document.getFileName());
            }

            // Create Resource
            Resource resource = new UrlResource(filePath.toUri());

            // Verify the resource exists and is readable
            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException("Could not read file: " + document.getFileName());
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            throw new RuntimeException("Error downloading file: " + e.getMessage(), e);
        }
    }
}
