package com.karot.mrs.backend.mapper;

import com.karot.mrs.backend.dto.Role;
import com.karot.mrs.backend.dto.request.RegisterRequest;
import com.karot.mrs.backend.dto.respond.UserDto;
import com.karot.mrs.backend.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder encoder;
    private final ModelMapper modelMapper ;

    public User toEntity(RegisterRequest request){
        User user = modelMapper.map(request, User.class);
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        return user;
    }

    public UserDto toDto(User user){
        return modelMapper.map(user,UserDto.class);
    }
}
