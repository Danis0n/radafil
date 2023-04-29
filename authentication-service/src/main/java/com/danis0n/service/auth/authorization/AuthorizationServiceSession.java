package com.danis0n.service.auth.authorization;

import com.danis0n.entity.Session;
import com.danis0n.enums.Role;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizationServiceSession extends AuthorizationService {

    private final RedisTemplate<String, Session> redis;

    @Override
    public Optional<Authentication> authorize(@NonNull HttpServletRequest request) {
        return extractBearerTokenHeader(request).flatMap(this::lookup);
    }

    private Optional<Authentication> lookup(String token) {
        try {
            Session session = this.redis.opsForValue().get(token);
            if (nonNull(session)) {
                Authentication authentication = createAuthentication(session.getUsername(), Role.USER);
                return Optional.of(authentication);
            }
            return Optional.empty();
        } catch (Exception e) {
            log.warn("Unknown error while trying to look up Redis token", e);
            return Optional.empty();
        }
    }
}
