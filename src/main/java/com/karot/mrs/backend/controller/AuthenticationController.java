package com.karot.mrs.backend.controller;

import com.karot.mrs.backend.dto.request.LoginRequest;
import com.karot.mrs.backend.dto.response.LoginResponse;
import com.karot.mrs.backend.exception.MrsException;
import com.karot.mrs.backend.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) throws MrsException {
        return new ResponseEntity<>(authenticationService.login(loginRequest), HttpStatus.OK);
    }
}
