package com.karot.mrs.backend.controller;

import com.karot.mrs.backend.dto.request.RegisterRequest;
import com.karot.mrs.backend.dto.respond.UserDto;
import com.karot.mrs.backend.exception.MrsException;
import com.karot.mrs.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto>register(@Valid @RequestBody RegisterRequest request) throws MrsException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(request));
    }
}
