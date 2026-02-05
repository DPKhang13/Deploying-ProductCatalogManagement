package com.example.project.service;

import com.example.project.dto.request.LoginRequest;
import com.example.project.dto.response.LoginResponse;
import com.example.project.dto.request.RegisterRequest;
import com.example.project.dto.response.RegisterResponse;
import com.example.project.entity.User;


public interface UserService {
    LoginResponse login(LoginRequest request);
    RegisterResponse register(RegisterRequest request);
    User getProfile(String email);

}
