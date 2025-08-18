package com.counterly.user.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class UserDto {
    private String username;
    private String email;
    private String password;

    private Set<String> userRoles;

    private Set<String> permissions;
}
