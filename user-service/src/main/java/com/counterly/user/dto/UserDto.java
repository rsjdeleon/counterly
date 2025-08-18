package com.counterly.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
@Builder
public class UserDto implements Serializable {

    @Null
    private String id;

    private String username;
    private String email;

    @Null
    private String password;

    private Set<String> userRoles;

    private Set<String> permissions;
}
