package com.counterly.user.web.mappers;

import com.counterly.user.dto.AuthUserDto;
import com.counterly.user.model.Authority;
import com.counterly.user.model.Role;
import com.counterly.user.model.User;

import java.util.Set;
import java.util.stream.Collectors;


public abstract class AuthUserMapperDecorator implements UserMapper {

    @Override
    public AuthUserDto userToAuthUserDto(User user) {
        return AuthUserDto.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .userRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
                .permissions(
                        user.getRoles().stream()
                                .flatMap(role -> role.getAuthorities().stream())
                                .map(Authority::getPermission)
                                .collect(Collectors.toSet()))
                .build();
    }
}
