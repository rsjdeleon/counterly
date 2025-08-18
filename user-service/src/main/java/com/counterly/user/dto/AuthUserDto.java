package com.counterly.user.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class AuthUserDto {

    private String username;
    private String email;
    private String password;

    private Set<String> userRoles;

    private Set<String> permissions;
}