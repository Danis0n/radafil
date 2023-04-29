package com.danis0n.service.auth.authorization;

import com.danis0n.service.auth.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

import java.util.Optional;

@Slf4j
public abstract class AuthorizationService extends AuthService {

    public abstract Optional<Authentication> authorize(@NonNull HttpServletRequest request);

}
