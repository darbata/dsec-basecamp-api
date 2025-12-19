package com.dsec.collab.core.service;

import com.dsec.collab.core.domain.user.User;
import com.dsec.collab.core.port.UserApi;
import com.dsec.collab.core.port.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserApi {

    public UserService() {}


    @Override
    public User getOrCreateUser(UUID id) {
        return null;
    }

    @Override
    public User putUser(String id, String email, String username) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return List.of();
    }

}