package com.example.project.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {
    private Long id;

    private String fullName;

    private String phone;

    private String address;

    private String email;

    private String role;

    private Boolean isActive;



}
