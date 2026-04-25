package com.docportal.document_portal.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.docportal.document_portal.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	
	@PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, String> request) {
        String result = authService.register(
            request.get("name"),
            request.get("email"),
            request.get("password")
        );
        return ResponseEntity.ok(result);
    }
	
	
	@PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> request) {
        String token = authService.login(
            request.get("email"),
            request.get("password")
        );
        return ResponseEntity.ok(token);
    }
	
}
