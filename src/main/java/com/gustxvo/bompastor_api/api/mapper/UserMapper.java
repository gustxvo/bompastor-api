package com.gustxvo.bompastor_api.api.mapper;

import com.gustxvo.bompastor_api.api.model.user.UserDto;
import com.gustxvo.bompastor_api.domain.model.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toModel(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }
}
