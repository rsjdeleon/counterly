package com.counterly.user.service;

import com.counterly.user.client.SecurityServiceClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final SecurityServiceClient userServiceClient;

    public CustomUserDetailsService(SecurityServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userServiceClient.getUserByUsername(username).getBody();
        assert user != null;
        return new CustomUserDetails(user);
    }

//    private Collection<? extends GrantedAuthority> convertToSpringAuthorities(Set<Authority> authorities) {
//        if (authorities != null && authorities.size() > 0){
//            return authorities.stream()
//                    .map(Authority::getPermission)
//                    .map(SimpleGrantedAuthority::new)
//                    .collect(Collectors.toSet());
//        } else {
//            return new HashSet<>();
//        }
//    }
}