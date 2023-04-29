package com.danis0n.controller;

import com.danis0n.dto.LoginRequestDto;
import com.danis0n.service.auth.authentication.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthenticationService service;

    @GetMapping
    public String test() {
        return "HELLO FROM AUTH MICRO-SERVICE";
    }

    @PostMapping("login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> login(@RequestBody LoginRequestDto request, HttpServletResponse response) {
        return ResponseEntity.ok(service.login(request, response));
    }

    @PostMapping("logout")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Boolean> logout(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(service.logout(request, response));
    }

    @GetMapping("system")
    @PreAuthorize("hasRole('SYSTEM')")
    public ResponseEntity<Boolean> validateSystem() {
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @GetMapping("user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Boolean> validateUser() {
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @GetMapping("admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> validateAdmin() {
        return ResponseEntity.ok(Boolean.TRUE);
    }
}
