package com.docportal.document_portal.service;

import com.docportal.document_portal.model.GeneratedDocument;
import com.docportal.document_portal.model.Template;
import com.docportal.document_portal.model.User;
import com.docportal.document_portal.repository.GeneratedDocumentRepository;
import com.docportal.document_portal.repository.TemplateRepository;
import com.docportal.document_portal.repository.UserRepository;
import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DocumentService {

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private GeneratedDocumentRepository generatedDocumentRepository;

    @Autowired
    private UserRepository userRepository;

    private static final String OUTPUT_DIR = "D:\\Roshini\\document-portal\\generated-docs\\";

    public GeneratedDocument generateDocument(Long templateId, String email, Map<String, String> values) throws Exception {

        // 1. Look up user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Fetch template
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new RuntimeException("Template not found"));

        // 3. Replace placeholders
        String content = template.getContent();
        for (Map.Entry<String, String> entry : values.entrySet()) {
            content = content.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        
        //4. basic HTML
        String htmlContent = "<!DOCTYPE html><html><body>" + content + "</body></html>";
        
        // 5. Create output directory if needed
        File dir = new File(OUTPUT_DIR);
        if (!dir.exists()) dir.mkdirs();

        // 6. Generate PDF
//        String fileName = OUTPUT_DIR + "doc_" + user.getId() + "_" + System.currentTimeMillis() + ".pdf";
        String sanitizedTemplateName = template.getName().replaceAll("[^a-zA-Z0-9]", "_");
        String recipientName = values.getOrDefault("name", "unknown").replaceAll("[^a-zA-Z0-9]", "_");
        String timestamp = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = OUTPUT_DIR + sanitizedTemplateName + "_" + recipientName + "_" + timestamp + ".pdf";
        HtmlConverter.convertToPdf(htmlContent, new FileOutputStream(fileName));


        // 7. Save record to DB
        GeneratedDocument record = new GeneratedDocument();
        record.setUserId(user.getId());
        record.setTemplateId(templateId);
        record.setTemplateName(template.getName());
        record.setOutputPath(fileName);
        generatedDocumentRepository.save(record);

        return record;
    }

    public List<GeneratedDocument> getHistory(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return generatedDocumentRepository.findByUserIdOrderByCreateAtDesc(user.getId());
    }

    public Optional<GeneratedDocument> getById(Long id) {
        return generatedDocumentRepository.findById(id);
    }
}