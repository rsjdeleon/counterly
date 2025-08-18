package com.counterly.user.service;

import com.counterly.user.dto.AuthorityDto;
import com.counterly.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final UserDto user;

    public Collection<String> getRoles() {
        return user.getUserRoles();
    }

    public Collection<String> getPermissions() {
        return user.getPermissions();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user.getPermissions() != null && !user.getPermissions().isEmpty()){
            Set<GrantedAuthority> authorities = new HashSet<>();
            // Add roles
            for (String role : user.getUserRoles()) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
            for (String permission : user.getPermissions()) {
                authorities.add(new SimpleGrantedAuthority(permission));
            }

            return authorities;
        } else {
            return new HashSet<>();
        }
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
