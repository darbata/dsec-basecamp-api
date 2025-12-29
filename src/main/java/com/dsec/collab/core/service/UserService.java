package com.dsec.collab.core.service;

import com.dsec.collab.adaptor.http.GithubUserAccessToken;
import com.dsec.collab.adaptor.http.GithubUserProfile;
import com.dsec.collab.core.domain.GithubAccessToken;
import com.dsec.collab.core.domain.GithubProfile;
import com.dsec.collab.core.domain.User;
import com.dsec.collab.core.port.IGithubProxy;
import com.dsec.collab.core.port.UserApi;
import com.dsec.collab.core.port.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserApi {

    private final UserRepository userRepository;
    private final IGithubProxy githubProxy;

    public UserService(UserRepository userRepository, IGithubProxy githubProxy) {
        this.userRepository = userRepository;
        this.githubProxy = githubProxy;
    }

    @Override
    public User getOrCreateUser(UUID id, String email, String name) {
        return userRepository.findById(id).orElseGet(() -> {
            try {
                User newUser = User.create(id, email, name);
                return userRepository.save(newUser);
            } catch (Exception e) {
                return userRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("No user with id: " + id));
            }
        });
    }

    @Override
    public void connectGithub(UUID id, String code) {

        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No user with id: " + id));


        // query github
        GithubUserAccessToken token = this.githubProxy.tokenExchange(code);
        GithubUserProfile profile = this.githubProxy.queryAuthenticatedUser(token.accessToken());

        // save profile found and token to db
        user.setGithubAccessToken(GithubAccessToken.create(
                token.accessToken(),
                token.expiresIn(),
                token.refreshToken(),
                token.refreshTokenExpiresIn(),
                token.scope(),
                token.tokenType()
        ));

        user.setGithubProfile(GithubProfile.create(
                profile.githubId(),
                profile.githubUsername(),
                profile.githubUrl(),
                profile.githubAvatarUrl()
        ));


        userRepository.save(user);
    }
}