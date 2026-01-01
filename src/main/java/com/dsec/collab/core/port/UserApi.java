package com.dsec.collab.core.port;

import com.dsec.collab.adaptor.http.dto.GithubRepositoryDTO;
import com.dsec.collab.adaptor.http.dto.UserDTO;

import java.util.List;
import java.util.UUID;

public interface UserApi {
    public UserDTO getOrCreateUser(UUID id, String email, String name);
    public void connectGithub(UUID id, String code);
    public List<GithubRepositoryDTO> getUserRepositories(UUID id);
}