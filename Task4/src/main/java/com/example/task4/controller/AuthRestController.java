package com.example.task4.controller;

import com.example.task4.dto.LoginDto;
import com.example.task4.dto.RegistrationDto;
import com.example.task4.entity.User;
import com.example.task4.security.AuthResponse;
import com.example.task4.security.JwtProvider;
import com.example.task4.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthRestController {
    @Autowired
    private AuthService authService;
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody LoginDto loginDto) {
        User user = authService.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());
        if (user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        String token = jwtProvider.generateToken(user.getEmail());
        return new ResponseEntity(new AuthResponse(token), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegistrationDto registrationDto) {
        if (authService.existsByEmail(registrationDto.getEmail())) {
            return new ResponseEntity<>("Unable to create. Email is already taken!", HttpStatus.CONFLICT);
        }
        if (authService.existsByName(registrationDto.getName())) {
            return new ResponseEntity<>("Unable to create. Name is already taken!", HttpStatus.CONFLICT);
        }
        authService.saveUser(registrationDto);
        return new ResponseEntity<>("User successfully registered", HttpStatus.CREATED);
    }
}
