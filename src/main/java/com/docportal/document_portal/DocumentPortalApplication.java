package com.docportal.document_portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.docportal")
public class DocumentPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocumentPortalApplication.class, args);
	}

}
