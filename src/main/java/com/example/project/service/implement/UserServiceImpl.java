package com.example.project.service.implement;


import com.example.project.dto.request.LoginRequest;
import com.example.project.dto.request.RegisterRequest;
import com.example.project.dto.response.LoginResponse;
import com.example.project.dto.response.RegisterResponse;
import com.example.project.entity.Role;
import com.example.project.entity.User;
import com.example.project.exception.category.UnauthorizedException;
import com.example.project.mapper.UserMapper;
import com.example.project.repository.UserRepository;
import com.example.project.security.JwtTokenProvider;
import com.example.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmailAndIsActiveTrue(request.getEmail())
                .orElseThrow(() ->
                        new UnauthorizedException("Invalid email or password")
                );

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {
            throw new UnauthorizedException("Invalid email or password");
        }

        String token = jwtTokenProvider.generateToken(user.getEmail());

        return userMapper.toLoginResponse(user, token);
    }


    @Override
    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("EMAIL_ALREADY_EXISTS");
        }

        if (userRepository.existsByPhone(request.getPhone())) {
            throw new IllegalStateException("PHONE_ALREADY_EXISTS");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.CUSTOMER);
        user.setIsActive(true);

        return userMapper.toRegisterResponse(userRepository.save(user));
    }
    @Override
    public User getProfile(String token) {

        if (token == null || token.isBlank()) {
            throw new RuntimeException("Token trống");
        }

        if (!jwtTokenProvider.validateToken(token)) {
            throw new RuntimeException("Token không hợp lệ");
        }

        String email = jwtTokenProvider.extractEmail(token);

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
    }





}
