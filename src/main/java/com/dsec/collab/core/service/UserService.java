package com.dsec.collab.core.service;

import com.dsec.collab.adaptor.http.GithubAccessTokenDTO;
import com.dsec.collab.adaptor.http.GithubProfileDTO;
import com.dsec.collab.adaptor.http.GithubRepositoryDTO;
import com.dsec.collab.adaptor.http.UserDTO;
import com.dsec.collab.core.domain.GithubAccessToken;
import com.dsec.collab.core.domain.GithubProfile;
import com.dsec.collab.core.domain.User;
import com.dsec.collab.core.port.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserApi {

    private final UserRepository userRepository;
    private final IGithubProxy githubProxy;
    private final IDTOMapper dtoMapper;
    private final TokenRefresherApi tokenRefresherApi;

    public UserService(UserRepository userRepository, IGithubProxy githubProxy, IDTOMapper dtoMapper, TokenRefresherApi tokenRefresherApi) {
        this.userRepository = userRepository;
        this.githubProxy = githubProxy;
        this.dtoMapper = dtoMapper;
        this.tokenRefresherApi = tokenRefresherApi;
    }

    @Override
    public UserDTO getOrCreateUser(UUID id, String email, String name) {
        User user = userRepository.findById(id).orElseGet(() -> {
            try {
                User newUser = User.create(id, email, name);
                return userRepository.save(newUser);
            } catch (Exception e) {
                System.out.println(e.getMessage());
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

    @Override
    public List<GithubRepositoryDTO> getUserRepositories(UUID id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No user with id: " + id)
        );

        if (!user.hasValidToken()) {
            tokenRefresherApi.validateToken(user);
        }

        return githubProxy.getUserOwnedRepositories(user.getGithubAccessToken());
    }
}