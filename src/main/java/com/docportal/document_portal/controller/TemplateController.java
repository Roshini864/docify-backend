package com.docportal.document_portal.controller;

import com.docportal.document_portal.model.Template;
import com.docportal.document_portal.security.JwtUtil;
import com.docportal.document_portal.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/templates")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping
    public ResponseEntity<Template> createTemplate(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Template template) {
        String token = authHeader.substring(7);
        String email = jwtUtil.extractUsername(token);
        return ResponseEntity.ok(templateService.createTemplate(
            template.getName(),
            template.getContent(),
            email
        ));
    }
    
//    @GetMapping
//    public ResponseEntity<List<Template>> getAllTemplates() {
//        return ResponseEntity.ok(templateService.getAllTemplates());
//    }
    
    @GetMapping
    public ResponseEntity<List<Template>> getAllTemplates(
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String email = jwtUtil.extractUsername(token);
        return ResponseEntity.ok(templateService.getTemplatesByUser(email));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Template> getTemplateById(@PathVariable Long id) {
        return ResponseEntity.ok(templateService.getTemplateById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Template> updateTemplate(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        Template template = templateService.updateTemplate(
            id,
            request.get("name"),
            request.get("content")
        );
        return ResponseEntity.ok(template);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTemplate(@PathVariable Long id) {
        templateService.deleteTemplate(id);
        return ResponseEntity.ok("Template deleted successfully");
    }
}