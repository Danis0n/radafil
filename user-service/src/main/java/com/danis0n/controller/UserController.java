package com.danis0n.controller;

import com.danis0n.dto.CreateUserDto;
import com.danis0n.dto.UserDto;
import com.danis0n.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello fro user-controller");
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("register")
    public ResponseEntity<String> create(@RequestBody CreateUserDto dto) {
        return ResponseEntity.ok(service.addUser(dto));
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> findUserByUUID(@PathVariable("id") String uuid) {
        return ResponseEntity.ok(service.findUserByUUID(uuid));
    }

}
