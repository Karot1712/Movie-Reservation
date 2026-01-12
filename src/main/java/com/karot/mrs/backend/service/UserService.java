package com.karot.mrs.backend.service;

import com.karot.mrs.backend.dto.Role;
import com.karot.mrs.backend.dto.request.RegisterRequest;
import com.karot.mrs.backend.dto.respond.UserDto;
import com.karot.mrs.backend.entity.User;
import com.karot.mrs.backend.exception.MrsException;
import com.karot.mrs.backend.mapper.UserMapper;
import com.karot.mrs.backend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto registerUser(RegisterRequest request) throws MrsException {
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new MrsException("USER_ALREADY_EXITS");
        }
        User user = userMapper.toEntity(request);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }
}
