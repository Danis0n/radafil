package com.danis0n.controller;

import com.danis0n.dto.AuthRequest;
import com.danis0n.service.old.AuthServiceSimple;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static java.util.Map.entry;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    @GetMapping
    public String test() {
        return "HELLO FROM AUTH MICRO-SERVICE";
    }

    @GetMapping("/system")
    @PreAuthorize("hasRole('SYSTEM')")
    public ResponseEntity<Boolean> system(Authentication authentication) {
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Boolean> user(Authentication authentication) {
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> admin(Authentication authentication) {
        return ResponseEntity.ok(Boolean.TRUE);
    }

}
