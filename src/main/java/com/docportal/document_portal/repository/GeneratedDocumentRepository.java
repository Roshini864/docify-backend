package com.docportal.document_portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.docportal.document_portal.model.GeneratedDocument;

public interface GeneratedDocumentRepository extends JpaRepository<GeneratedDocument, Long> {
	List<GeneratedDocument> findByUserIdOrderByCreateAtDesc(Long userId);
}
