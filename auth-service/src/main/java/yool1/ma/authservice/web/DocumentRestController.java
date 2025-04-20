package yool1.ma.authservice.web;

import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yool1.ma.authservice.dto.DocumentDto;
import yool1.ma.authservice.entities.Document;
import yool1.ma.authservice.repository.DocumentRepository;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class DocumentRestController {

    private final DocumentRepository documentRepository;

    public DocumentRestController(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }



    //--------------------- DOCUMENT ---------------------------------------

    @PostMapping("/document/uploadDocument")
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


    @GetMapping(path = "/document/allDocs")
    public List<Document> AllDocuments() {
        return documentRepository.findAll();
    }


    @GetMapping("/document/downloadDocument/{id}")
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
