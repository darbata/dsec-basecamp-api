package com.dsec.collab.adaptor.http;

import com.dsec.collab.core.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static UserDTO getUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.isGithubConnected(),
                user.getGithubId(),
                user.getGithubUser(),
                user.getGithubUrl(),
                user.getGithubAvatarUrl(),
                user.isAdmin(),
                user.isMember()
        );
    }
}
