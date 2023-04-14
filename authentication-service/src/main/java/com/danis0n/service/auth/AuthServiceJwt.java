package com.danis0n.service.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.danis0n.enums.Role;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
@Slf4j
public class AuthServiceJwt extends AuthService {

    private final JWTVerifier jwtVerifier;

    public AuthServiceJwt(@Value("${auth.jwt.hmacKey}") String hmacKey) {
        Algorithm algorithm = Algorithm.HMAC256(hmacKey.getBytes(StandardCharsets.UTF_8));
        this.jwtVerifier = JWT.require(algorithm).build();
    }

    @Override
    public Optional<Authentication> authenticate(HttpServletRequest request) {
        log.info("HERE");
        return extractBearerTokenHeader(request).flatMap(this::verify);
    }

    private Optional<Authentication> verify(String token) {
        try {
            DecodedJWT jwt = this.jwtVerifier.verify(token);
            String issuer = jwt.getIssuer();
            Authentication authentication = createAuthentication(issuer, Role.ADMIN);

            return Optional.of(authentication);

        } catch (JWTDecodeException e) {
            return Optional.empty();

        } catch (Exception e) {
            log.warn("Unknown error while trying to verify JWT token", e);
            return Optional.empty();
        }
    }


}
