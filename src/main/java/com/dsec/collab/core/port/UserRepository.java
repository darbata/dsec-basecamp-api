package com.dsec.collab.core.port;

import com.dsec.collab.core.domain.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    public User save(User user); // the id may never update
    public Optional<User> findById(UUID id); // rehydrating user
}
