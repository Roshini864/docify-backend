package com.docportal.document_portal.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.docportal.document_portal.model.User;
import com.docportal.document_portal.repository.UserRepository;
import com.docportal.document_portal.security.JwtUtil;

@Service
public class AuthService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	public String register(String name, String email, String password) {
		if(userRepository.findByEmail(email).isPresent()) {
			throw new RuntimeException("Email already exists");
		}
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));
		userRepository.save(user);
		return "User registered successfully";
	}
	
	public String login(String email, String pasword) {
		Optional<User> userOpt = userRepository.findByEmail(email);
		if(userOpt.isEmpty()) {
			throw new RuntimeException("User not found");
		}
		User user = userOpt.get();
		if(!passwordEncoder.matches(pasword, user.getPassword())) {
			throw new RuntimeException("Invalid password");
		}
		return jwtUtil.generateToken(email);
	}

}
