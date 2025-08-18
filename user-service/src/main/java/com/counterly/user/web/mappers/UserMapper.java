package com.counterly.user.web.mappers;

import com.counterly.user.dto.AuthUserDto;
import com.counterly.user.dto.UserDto;
import com.counterly.user.model.User;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class, componentModel = "spring")
@DecoratedWith(AuthUserMapperDecorator.class)
public interface UserMapper {

    User userDtoToUser(UserDto userDto);

    UserDto userToUserDto(User user);

    AuthUserDto userToAuthUserDto(User user);

}
