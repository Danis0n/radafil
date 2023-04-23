package com.danis0n.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    @GetMapping
    public String test() {
        return "HELLO FROM AUTH MICRO-SERVICE";
    }

    @PostMapping("/register")
    public void register() {}


    @GetMapping("/system")
    @PreAuthorize("hasRole('SYSTEM')")
    public ResponseEntity<Boolean> system(Authentication authentication) {
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Boolean> validateUser(Authentication authentication) {
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> validateAdmin(Authentication authentication) {
        return ResponseEntity.ok(Boolean.TRUE);
    }
}
