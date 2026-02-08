package com.karot.mrs.backend.service;

import com.karot.mrs.backend.dto.request.LoginRequest;
import com.karot.mrs.backend.dto.response.LoginResponse;
import com.karot.mrs.backend.exception.MrsException;
import com.karot.mrs.backend.mapper.UserMapper;
import com.karot.mrs.backend.security.AuthUser;
import com.karot.mrs.backend.security.CustomUserDetailService;
import com.karot.mrs.backend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailService userDetailService;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;

    public LoginResponse login(LoginRequest request) throws MrsException {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
            AuthUser userDetails =(AuthUser) userDetailService.loadUserByUsername(request.getEmail());
            String token = jwtUtil.generateToken(userDetails);
            LoginResponse response = userMapper.toLoginResponse(userDetails, token);
            response.setExpiresAt(jwtUtil.getTokenExpirationDate(token));

            return response;

        } catch (AuthenticationException e) {
            log.error("Authentication failed for: {}",request.getEmail());
            throw new MrsException("INVALID_CREDENTIALS");
        }
    }
}
