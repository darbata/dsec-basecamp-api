package com.dsec.collab.adaptor.http.user;

import com.dsec.collab.core.domain.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static UserDTO getUserDTO(User user) {
        return new UserDTO(user.getId(), user.getEmail(), user.getName());
    }
}
