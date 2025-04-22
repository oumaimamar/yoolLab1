package yool1.ma.authservice.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import yool1.ma.authservice.dto.DocumentDto;
import yool1.ma.authservice.entities.Document;
import yool1.ma.authservice.entities.User;
import yool1.ma.authservice.repository.DocumentRepository;
import yool1.ma.authservice.repository.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional
public class DocumentService {

    private final DocumentRepository documentRepository;
    private UserRepository userRepository;

    public DocumentService(DocumentRepository documentRepository,UserRepository userRepository) {
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
    }


    //---------------------Add DOCUMENT --------------------
    public ResponseEntity<?> addDocumentToUser(@Valid DocumentDto documentDto,
        BindingResult result, Map<String, Object> response) throws IOException {

        // Image/Document Required validation
        if (documentDto.getFilePath().isEmpty()) {
            result.addError(new FieldError("documentDto", "filePath", "The document file is required"));
        }

        if (result.hasErrors()) {
            response.put("status", "error");
            response.put("errors", result.getAllErrors());
            return ResponseEntity.badRequest().body(response);
        }

        // Find the user
        User user = userRepository.findById(documentDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Save the document file with timestamp prefix
        MultipartFile docFile = documentDto.getFilePath();
        Date dateDoc = new Date();
        String storageFileName = dateDoc.getTime() + "_" + docFile.getOriginalFilename();

        try {
            String uploadDir = "public/Doc/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = docFile.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
                        StandardCopyOption.REPLACE_EXISTING);
            }

            // Create and save the document
            Document document = new Document();
            document.setTitre(documentDto.getTitre());
            document.setTypeDoc(documentDto.getTypeDoc());
            document.setDateAjout(dateDoc);
            document.setFileName(storageFileName);
            document.setUser(user);

            Document savedDocument = documentRepository.save(document);
            user.getDocuments().add(savedDocument);

            response.put("status", "success");
            response.put("document", savedDocument);
            return ResponseEntity.ok(response);

        } catch (IOException e) {
            response.put("status", "error");
            response.put("message", "Failed to store document file");
            return ResponseEntity.internalServerError().body(response);
        }
    }




    //---------------------Delete DOCUMENT --------------------
    public ResponseEntity<?> deleteDocument(Long documentId, Map<String, Object> response) {
        try {
            // Find the document in database
            Document document = documentRepository.findById(documentId)
                    .orElseThrow(() -> new RuntimeException("Document not found"));

            // Delete the file from storage
            String filePath = "public/Doc/" + document.getFileName();
            Path path = Paths.get(filePath);

            if (Files.exists(path)) {
                Files.delete(path);
            }

            // Remove from user's documents list (optional but cleaner)
            document.getUser().getDocuments().removeIf(doc -> doc.getId().equals(documentId));

            // Delete from database
            documentRepository.delete(document);

            response.put("status", "success");
            response.put("message", "Document deleted successfully");
            return ResponseEntity.ok(response);

        } catch (NoSuchFileException e) {
            response.put("status", "error");
            response.put("message", "File not found: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (IOException e) {
            response.put("status", "error");
            response.put("message", "Failed to delete file: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error deleting document: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }


//    public ResponseEntity<Map<String, Object>> addDocument(
//            DocumentDto documentDto,
//            Long userId,  // Added: To associate document with a user
//            BindingResult result) {
//
//        Map<String, Object> response = new HashMap<>();
//
//        // 1. Validate Input
//        if (documentDto.getFilePath() == null || documentDto.getFilePath().isEmpty()) {
//            result.addError(new FieldError("documentDto", "filePath", "Document file is required"));
//        }
//
//        if (result.hasErrors()) {
//            response.put("status", "error");
//            response.put("errors", result.getAllErrors());
//            return ResponseEntity.badRequest().body(response);
//        }
//
//        // 2. Check if User exists
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new EntityNotFoundException("User not found"));
//
//        // 3. File Handling
//        MultipartFile file = documentDto.getFilePath();
//        String originalFilename = file.getOriginalFilename();
//        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
//        String storageFileName = System.currentTimeMillis() + fileExtension; // Unique filename
//        String uploadDir = "public/Doc/";
//
//        try {
//            // Create directory if not exists
//            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
//            Files.createDirectories(uploadPath);
//
//            // Secure file storage (prevent path traversal)
//            Path filePath = uploadPath.resolve(storageFileName).normalize();
//            if (!filePath.startsWith(uploadPath)) {
//                throw new IOException("Invalid file path");
//            }
//
//            // Save file
//            try (InputStream inputStream = file.getInputStream()) {
//                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
//            }
//        } catch (IOException ex) {
//            response.put("status", "error");
//            response.put("message", "Failed to store document file: " + ex.getMessage());
//            return ResponseEntity.internalServerError().body(response);
//        }
//
//        // 4. Save Document to Database
//        Document document = new Document();
//        document.setTitre(documentDto.getTitre());
//        document.setTypeDoc(documentDto.getTypeDoc());
//        document.setDateAjout(new Date());
//        document.setFileName(storageFileName);
//        document.setUser(documentDto.getUserId());  // Associate with User
//
//        Document savedDocument = documentRepository.save(document);
//
//        // 5. Return Success Response
//        response.put("status", "success");
//        response.put("message", "Document uploaded successfully");
//        response.put("document", savedDocument);
//
//        return ResponseEntity.ok(response);
//    }


    //---------------------download DOCUMENT --------------------
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