package com.karot.mrs.backend.dto.respond;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoginRespond {
    private String userId;
    private String email;
    private String message;
}
