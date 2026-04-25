package com.docportal.document_portal.controller;

import com.docportal.document_portal.model.GeneratedDocument;
import com.docportal.document_portal.service.DocumentService;
import com.docportal.document_portal.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private JwtUtil jwtUtil;

    // POST /api/documents/generate
    // Body: { "templateId": 1, "values": { "name": "Roshini", "date": "2026-04-18" } }
    @PostMapping("/generate")
    public ResponseEntity<?> generate(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Map<String, Object> body) {
        try {
            String token = authHeader.substring(7);
            String email = jwtUtil.extractUsername(token);

            Long templateId = Long.valueOf(body.get("templateId").toString());
            Map<String, String> values = (Map<String, String>) body.get("values");

            GeneratedDocument doc = documentService.generateDocument(templateId, email, values);
            return ResponseEntity.ok(doc);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // GET /api/documents/history
    @GetMapping("/history")
    public ResponseEntity<List<GeneratedDocument>> getHistory(
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String email = jwtUtil.extractUsername(token);
        return ResponseEntity.ok(documentService.getHistory(email));
    }

    // GET /api/documents/download/{id}
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable Long id) {
        Optional<GeneratedDocument> docOpt = documentService.getById(id);
        if (docOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        File file = new File(docOpt.get().getOutputPath());
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}