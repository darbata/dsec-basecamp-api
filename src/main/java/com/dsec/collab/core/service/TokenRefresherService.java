package com.dsec.collab.core.service;

import com.dsec.collab.adaptor.http.GithubAccessTokenDTO;
import com.dsec.collab.core.domain.User;
import com.dsec.collab.core.exception.GithubAuthenticationException;
import com.dsec.collab.core.port.IDTOMapper;
import com.dsec.collab.core.port.IGithubProxy;
import com.dsec.collab.core.port.TokenRefresherApi;
import com.dsec.collab.core.port.UserRepository;
import org.springframework.stereotype.Service;

import javax.naming.NoPermissionException;


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

        if (user.hasValidToken()) return;

        if (user.getGithubAccessToken() == null) {
            throw new GithubAuthenticationException("user does not have a valid token to refresh");
        }

        // get new token from github
        GithubAccessTokenDTO newTokenDTO = githubProxy.refreshToken(
            user.getGithubAccessToken().getRefreshToken()
        );

        // if error, clear user's github connection and throw exception
        if (newTokenDTO.error() != null) {
            user.setGithubProfile(null);
            user.setGithubAccessToken(null);
            this.userRepository.save(user);
            throw new RuntimeException("Github refresh token failed " + newTokenDTO.error());
        }

        // got new token suc
        user.setGithubAccessToken(dtoMapper.toEntity(newTokenDTO));
        this.userRepository.save(user);

    }
}
