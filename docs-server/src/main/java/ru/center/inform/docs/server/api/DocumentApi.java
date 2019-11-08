package ru.center.inform.docs.server.api;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.center.inform.docs.server.domain.Document;
import ru.center.inform.docs.server.domain.Views;
import ru.center.inform.docs.server.services.DocumentService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DocumentApi {

    private final DocumentService documentService;

    @GetMapping("/api/document/{id}")
    @JsonView(Views.Document.class)
    public ResponseEntity<Document> get(@PathVariable("id") Long id) {
        log.info("Get document by {}", id);
        Document document = documentService.getLazy(id);
        return ResponseEntity.ok().body(document);
    }

    @GetMapping("/api/document/{id}/content")
    @JsonView(Views.DocumentFull.class)
    public ResponseEntity<Document> getWithContent(@PathVariable("id") Long id) {
        log.info("Get document with content by {}", id);
        Document document = documentService.get(id);
        return ResponseEntity.ok().body(document);
    }

    @GetMapping("/api/document")
    public ResponseEntity<List<Long>> getAllIds() {
        log.info("Get all document ids");
        List<Long> documentIds = documentService.getAllIds();
        return ResponseEntity.ok().body(documentIds);
    }

    @PostMapping("/api/document")
    public ResponseEntity<Document> createDocument(@Valid @RequestBody Document document) {
        log.info("Create document {}", document);
        Document savedDocument = documentService.save(document);
        return new ResponseEntity<>(savedDocument, HttpStatus.CREATED);
    }

}
