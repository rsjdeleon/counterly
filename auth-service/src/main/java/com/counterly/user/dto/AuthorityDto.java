package com.counterly.user.dto;

import lombok.Data;

import java.util.Set;

@Data
public class AuthorityDto {
    private String permission;
    private Set<RoleDto> roles;
}
