package com.danis0n.service.auth.authentication;

import com.danis0n.dto.LoginRequestDto;
import com.danis0n.service.auth.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;

public abstract class AuthenticationService extends AuthService {

    public abstract String authenticate(LoginRequestDto request, HttpServletResponse response);
    public abstract Boolean logout(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response);

}
