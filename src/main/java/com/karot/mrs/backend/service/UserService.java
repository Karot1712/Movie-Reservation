package com.karot.mrs.backend.service;


import com.karot.mrs.backend.dto.request.RegisterRequest;
import com.karot.mrs.backend.dto.response.UserDto;
import com.karot.mrs.backend.entity.User;

import com.karot.mrs.backend.exception.MrsException;
import com.karot.mrs.backend.mapper.UserMapper;
import com.karot.mrs.backend.repositories.UserRepository;

import com.karot.mrs.backend.security.JwtUtil;

import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto registerUser(RegisterRequest request) throws MrsException {
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new MrsException("USER_ALREADY_EXISTS");
        }
        User user = userMapper.toEntity(request);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public UserDto getUserById(Long userId) throws MrsException{
        User user = userRepository.findById(userId).orElseThrow(()-> new MrsException("USER_NOT_FOUND"));
        return userMapper.toDto(user);
    }

    public List<UserDto> getAllUser()throws MrsException{
        return userRepository.findAll().stream().map(userMapper::toDto).toList();
    }
}
