package com.danis0n.client;

import com.danis0n.dto.AuthenticationData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface UserClient {

    @GetExchange("/api/v1/user/retrieve-credentials")
    ResponseEntity<AuthenticationData> retrieveCredentialsByUsername(@RequestParam("username") String username);

}
