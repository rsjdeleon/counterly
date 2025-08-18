package com.counterly.user.web.controller;


import com.counterly.user.dto.AuthUserDto;
import com.counterly.user.dto.UserDto;
import com.counterly.user.request.RegisterRequest;
import com.counterly.user.request.UserUpdateRequest;
import com.counterly.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/save")
    public ResponseEntity<UserDto> save(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.saveUser(request));
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('product_read')")
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN') or @userService.getUserById(#request.id).username == principal")
    public ResponseEntity<UserDto> updateUserById(@Valid @RequestPart UserUpdateRequest request,
                                                  @RequestPart(required = false) MultipartFile file) {
        return ResponseEntity.ok(userService.updateUserById(request));
    }

    @DeleteMapping("/deleteUserById/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userService.getUserById(#id).username == principal")
    public ResponseEntity<Void> deleteUserById(@PathVariable String id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }
}
