package com.counterly.user.web.controller;


import com.counterly.user.dto.AuthUserDto;
import com.counterly.user.dto.UserDto;
import com.counterly.user.request.RegisterRequest;
import com.counterly.user.request.UserUpdateRequest;
import com.counterly.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "tag_at_class_level", description = "Books related class level tag")
public class UserController {
    private final UserService userService;

    @Tag(name = "create")
    @Tag(name = "common_tag_at_method_level")
    @Tag(name = "createBook")
    @PostMapping("/save")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true)
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

    @Tag(name = "delete")
    @Tag(name = "common_tag_at_method_level")
    @Tag(name = "deleteBook")
    @DeleteMapping("/deleteUserById/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userService.getUserById(#id).username == principal")
    public ResponseEntity<Void> deleteUserById(@PathVariable String id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }
}
