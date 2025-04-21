package yool1.ma.authservice.web;

import jakarta.validation.Valid;
import org.springframework.core.io.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import yool1.ma.authservice.dto.DocumentDto;
import yool1.ma.authservice.entities.Document;
import yool1.ma.authservice.repository.DocumentRepository;
import yool1.ma.authservice.services.DocumentService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class DocumentRestController {

    private final DocumentRepository documentRepository;
    private final DocumentService documentService;

    public DocumentRestController(DocumentRepository documentRepository, DocumentService documentService) {
        this.documentRepository = documentRepository;
        this.documentService = documentService;
    }

    //---------------------List DOCUMENT --------------------
    @GetMapping(path = "/document/allDocs")
    public List<Document> AllDocuments() {
        return documentRepository.findAll();
    }

    //---------------------Add DOCUMENT --------------------
    @PostMapping("/document/uploadDocument")
    public ResponseEntity<?> addDocument(@Valid @ModelAttribute DocumentDto documentDto,
                                         BindingResult bindingResult) throws IOException {
        Map<String, Object> response = new HashMap<>();
        return documentService.addDocumentToUser(documentDto, bindingResult, response);
    }

//    ---------------------Add DOCUMENT --------------------
//    @PostMapping("/users/{userId}/documents")
//    public ResponseEntity<Map<String, Object>> uploadDocument(
//            @PathVariable Long userId,
//            @Valid @ModelAttribute DocumentDto documentDto,
//            BindingResult result) {
//        return documentService.addDocument(documentDto, userId, result);
//    }

    //---------------------download DOCUMENT --------------------
    @GetMapping("/document/downloadDocument/{id}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long id) {
        return documentService.downloadDocument(id);
    }


}