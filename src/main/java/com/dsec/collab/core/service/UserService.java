package com.dsec.collab.core.service;

import com.dsec.collab.adaptor.http.GithubAccessTokenDTO;
import com.dsec.collab.adaptor.http.GithubProfileDTO;
import com.dsec.collab.adaptor.http.UserDTO;
import com.dsec.collab.core.domain.GithubAccessToken;
import com.dsec.collab.core.domain.GithubProfile;
import com.dsec.collab.core.domain.User;
import com.dsec.collab.core.port.IDTOMapper;
import com.dsec.collab.core.port.IGithubProxy;
import com.dsec.collab.core.port.UserApi;
import com.dsec.collab.core.port.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService implements UserApi {

    private final UserRepository userRepository;
    private final IGithubProxy githubProxy;
    private final IDTOMapper dtoMapper;

    public UserService(UserRepository userRepository, IGithubProxy githubProxy, IDTOMapper dtoMapper) {
        this.userRepository = userRepository;
        this.githubProxy = githubProxy;
        this.dtoMapper = dtoMapper;
    }

    @Override
    public UserDTO getOrCreateUser(UUID id, String email, String name) {
        User user = userRepository.findById(id).orElseGet(() -> {
            try {
                User newUser = User.create(id, email, name);
                return userRepository.save(newUser);
            } catch (Exception e) {
                return userRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("No user with id: " + id));
            }
        });

        return dtoMapper.toDTO(user);
    }

    @Override
    public void connectGithub(UUID id, String code) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No user with id: " + id)
        );

        GithubAccessTokenDTO tokenDTO = this.githubProxy.tokenExchange(code);

        GithubAccessToken token = dtoMapper.toEntity(tokenDTO);

        GithubProfileDTO profileDTO = this.githubProxy.queryAuthenticatedUser(token);

        GithubProfile profile = dtoMapper.toEntity(profileDTO);

        user.setGithubAccessToken(token);

        user.setGithubProfile(profile);

        userRepository.save(user);
    }

}