package com.dsec.collab.core.port;

import com.dsec.collab.core.domain.user.User;

import java.util.UUID;

public interface UserRepository {
    public User findById(UUID id); // rehydrating user
    public User save(UUID id, User user); // the id may never update
}
