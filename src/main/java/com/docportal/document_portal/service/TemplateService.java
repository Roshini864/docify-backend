package com.docportal.document_portal.service;

import com.docportal.document_portal.model.Template;
import com.docportal.document_portal.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateService {

    @Autowired
    private TemplateRepository templateRepository;

    public Template createTemplate(String name, String content, String createdBy) {
        Template template = new Template();
        template.setName(name);
        template.setContent(content);
        template.setCreatedBy(createdBy);
        return templateRepository.save(template);
    }

    public List<Template> getAllTemplates() {
        return templateRepository.findAll();
    }

    public Template getTemplateById(Long id) {
        return templateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Template not found"));
    }
    
    public List<Template> getTemplatesByUser(String email) {
        return templateRepository.findByCreatedBy(email);
    }

    public Template updateTemplate(Long id, String name, String content) {
        Template template = getTemplateById(id);
        template.setName(name);
        template.setContent(content);
        return templateRepository.save(template);
    }

    public void deleteTemplate(Long id) {
        templateRepository.deleteById(id);
    }
}