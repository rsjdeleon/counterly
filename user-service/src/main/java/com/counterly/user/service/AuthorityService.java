package com.counterly.user.service;


import com.counterly.user.dto.AuthUserDto;
import com.counterly.user.dto.AuthorityDto;
import com.counterly.user.dto.UserDto;
import com.counterly.user.request.RegisterRequest;
import com.counterly.user.request.UserUpdateRequest;

import java.util.List;

public interface AuthorityService {

    UserDto saveUser(RegisterRequest request);

    List<UserDto> getAll();

    UserDto getUserById(String email);

    AuthorityDto findByRo(String email);

    AuthUserDto getUserByUsername(String username);

    UserDto updateUserById(UserUpdateRequest request);

    void deleteUserById(String id);


}