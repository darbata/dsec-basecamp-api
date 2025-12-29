package com.dsec.collab.core.port;

import com.dsec.collab.core.domain.User;

import java.util.UUID;

public interface UserApi {
    public User getOrCreateUser(UUID id, String email, String name);
    public void connectGithub(UUID id, String code);
}