package com.counterly.user.service;


import com.counterly.user.constants.RoleEnum;
import com.counterly.user.dto.AuthUserDto;
import com.counterly.user.dto.UserDto;
import com.counterly.user.exceptions.NotFoundException;
import com.counterly.user.model.Role;
import com.counterly.user.model.User;
import com.counterly.user.repository.RoleRepository;
import com.counterly.user.repository.UserRepository;
import com.counterly.user.request.RegisterRequest;
import com.counterly.user.request.UserUpdateRequest;
import com.counterly.user.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserDto saveUser(RegisterRequest request) {
        Optional<Role> roleOptional = roleRepository.findByName(RoleEnum.USER.toString());
        if (roleOptional.isEmpty()) {
            throw new NotFoundException("Not Able to find role: user");
        }
        User toSave = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(roleOptional.get()).build();

        return userMapper.userToUserDto(userRepository.save(toSave));
    }



    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map(userMapper::userToUserDto).toList();
    }

    @Override
    public UserDto getUserById(String id) {
        return userMapper.userToUserDto(userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found")));
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return userMapper.userToUserDto(userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found")));
    }

    @Override
    public AuthUserDto getUserByUsername(String username) {
        return userMapper.userToAuthUserDto(userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found")));
    }

    @Override
    public UserDto updateUserById(UserUpdateRequest request) {
        Optional<User> toUpdate = userRepository.findById(request.getId());
        if (toUpdate.isPresent()) {
            return userMapper.userToUserDto(userRepository.save(toUpdate.get()));
        } else {
            throw new NotFoundException("User not found");
        }
    }

    @Override
    public void deleteUserById(String id) {
        Optional<User> toDelete = userRepository.findById(id);
        if (toDelete.isPresent()) {
            toDelete.get().setActive(false);
            userRepository.save(toDelete.get());
        } else {
            throw new NotFoundException("User not found");
        }
    }

    //    private final FileStorageClient fileStorageClient;
    //    private final ModelMapper modelMapper;

    //    public UserDto updateUserById(UserUpdateRequest request, MultipartFile file) {
    //        User toUpdate = findUserById(request.getId());
    //
    //        request.setUserDetails(updateUserDetails(toUpdate.getUserDetails(), request.getUserDetails(), file));
    //        modelMapper.map(request, toUpdate);
    //
    //        return userRepository.save(toUpdate);
    //    }

    //    private UserDetails updateUserDetails(UserDetails toUpdate, UserDetails request, MultipartFile file) {
    //        toUpdate = toUpdate == null ? new UserDetails() : toUpdate;
    //
    //        if (file != null) {
    //            String profilePicture = fileStorageClient.uploadImageToFIleSystem(file).getBody();
    //            if (profilePicture != null) {
    //                fileStorageClient.deleteImageFromFileSystem(toUpdate.getProfilePicture());
    //                toUpdate.setProfilePicture(profilePicture);
    //            }
    //        }
    //
    //        modelMapper.map(request, toUpdate);
    //
    //        return toUpdate;
    //    }
}