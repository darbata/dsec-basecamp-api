package com.dsec.collab.core.port;

import com.dsec.collab.adaptor.http.UserDTO;

import java.util.UUID;

public interface UserApi {
    public UserDTO getOrCreateUser(UUID id, String email, String name);
    public void connectGithub(UUID id, String code);
}