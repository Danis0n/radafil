package com.danis0n.controller;

import com.danis0n.dto.AuthenticationData;
import com.danis0n.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/credentials")
public class CredentialsController {

    private final UserService userService;
    private final PasswordEncoder BCRYPT;

    @GetMapping("admin")
    public ResponseEntity<AuthenticationData> retrieveAdminCredentialsByUsername(
            @RequestParam("username") String username
    ) {
        return ResponseEntity.ok(
                new AuthenticationData(username, "1234")
        );
    }

    @GetMapping("user")
    public ResponseEntity<AuthenticationData> retrieveUserCredentialsByUsername(
            @RequestParam("username") String username
    ) {
        return ResponseEntity.ok(
                new AuthenticationData(username, BCRYPT.encode("1234"))
        );
    }

}
