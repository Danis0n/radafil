package com.danis0n.controller;

import com.danis0n.dto.AuthenticationData;
import com.danis0n.dto.CreateUserDto;
import com.danis0n.dto.UserDto;
import com.danis0n.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello fro user-controller");
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody CreateUserDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUserByUUID(@PathVariable("id") String uuid) {
        return ResponseEntity.ok(service.findUserByUUID(uuid));
    }

    @GetMapping("retrieve-credentials")
    public ResponseEntity<AuthenticationData> retrieveCredentialsByUsername(@RequestParam("username") String username) {
        return ResponseEntity.ok(
                AuthenticationData.builder()
                    .username(username)
                    .hashedPassword("1234")
                    .build());
    }

}
