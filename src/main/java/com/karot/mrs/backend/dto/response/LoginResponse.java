package com.karot.mrs.backend.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private Long userId;
    private String token;
    private String tokenType;
    private String email;
    private String firstName;
    private String lastName;
    private Date expiresAt;
}
