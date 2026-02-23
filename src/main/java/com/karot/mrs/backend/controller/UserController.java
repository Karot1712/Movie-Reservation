package com.karot.mrs.backend.controller;

import com.karot.mrs.backend.dto.request.RegisterRequest;
import com.karot.mrs.backend.dto.response.UserDto;
import com.karot.mrs.backend.exception.MrsException;
import com.karot.mrs.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto>register(@Valid @RequestBody RegisterRequest request) throws MrsException {
        return new ResponseEntity<>(userService.registerUser(request),HttpStatus.CREATED);
    }

    @GetMapping("/getUserById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) throws MrsException{
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/getAllUser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUser()throws MrsException{
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }
}
