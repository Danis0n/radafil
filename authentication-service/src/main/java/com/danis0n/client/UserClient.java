package com.danis0n.client;

import com.danis0n.dto.AuthenticationDataDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface UserClient {

    @GetExchange("/api/v1/credentials/admin")
    ResponseEntity<AuthenticationDataDto> retrieveAdminCredentialsByUsername(@RequestParam("username") String username);

    @GetExchange("/api/v1/credentials/user")
    ResponseEntity<AuthenticationDataDto> retrieveUserCredentialsByUsername(@RequestParam("username") String username);

}
