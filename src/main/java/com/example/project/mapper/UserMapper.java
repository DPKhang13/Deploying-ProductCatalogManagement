package com.example.project.mapper;

import com.example.project.dto.request.RegisterRequest;
import com.example.project.dto.response.LoginResponse;
import com.example.project.dto.response.RegisterResponse;
import com.example.project.entity.Role;
import com.example.project.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = Role.class)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "CUSTOMER")
    @Mapping(target = "isActive", constant = "true")
    User toEntity(RegisterRequest request);

    @Mapping(target = "role", source = "role")
    RegisterResponse toRegisterResponse(User user);

    @Mapping(target = "token", source = "token")
    @Mapping(target = "role", expression = "java(user.getRole().name())")
    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "fullName", source = "user.fullName")
    LoginResponse toLoginResponse(User user, String token);
}
