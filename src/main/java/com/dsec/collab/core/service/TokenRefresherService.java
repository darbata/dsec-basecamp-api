package com.dsec.collab.core.service;

import com.dsec.collab.adaptor.http.GithubAccessTokenDTO;
import com.dsec.collab.core.domain.GithubAccessToken;
import com.dsec.collab.core.domain.User;
import com.dsec.collab.core.port.IDTOMapper;
import com.dsec.collab.core.port.IGithubProxy;
import com.dsec.collab.core.port.TokenRefresherApi;
import com.dsec.collab.core.port.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class TokenRefresherService implements TokenRefresherApi {

    private final UserRepository userRepository;
    private final IGithubProxy githubProxy;
    private final IDTOMapper dtoMapper;

    public TokenRefresherService(UserRepository userRepository, IGithubProxy githubProxy, IDTOMapper dtoMapper) {
        this.userRepository = userRepository;
        this.githubProxy = githubProxy;
        this.dtoMapper = dtoMapper;
    }

    @Override
    public void validateToken(User user) {

        System.out.println("GithubProxy refreshToken");
        System.out.println("Current (OLD) token: " + user.getGithubAccessToken().getAccessToken());

        if (!user.hasValidToken()) {

            System.out.println("USER DOES NOT HAVE VALID TOKEN");

            GithubAccessTokenDTO newTokenDTO = githubProxy.refreshToken(
                    user.getGithubAccessToken().getRefreshToken()
            );

            GithubAccessToken newToken = dtoMapper.toEntity(newTokenDTO);

            user.setGithubAccessToken(newToken);

            System.out.println("CREATED NEW TOKEN" + user.getGithubAccessToken().getAccessToken());

            userRepository.save(user);
        }
    }
}
