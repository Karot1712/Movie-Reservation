package com.karot.mrs.backend.dto.respond;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
//    private String token;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String role;
    private LocalDateTime createdAt;
}
