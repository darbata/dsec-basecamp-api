package com.dsec.collab.adaptor.http.proxy;

import com.dsec.collab.adaptor.http.dto.GithubAccessTokenDTO;
import com.dsec.collab.adaptor.http.dto.GithubProfileDTO;
import com.dsec.collab.adaptor.http.dto.GithubRepositoryDTO;
import com.dsec.collab.core.domain.GithubAccessToken;
import com.dsec.collab.core.port.IGithubProxy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GithubProxy implements IGithubProxy {

    @Value("${spring.security.oauth2.client.github.client-id}")
    private String githubClientId;

    @Value("${spring.security.oauth2.client.github.client-secret}")
    private String githubClientSecret;

    private final GithubApiClient githubApiClient;

    public GithubProxy(GithubApiClient githubApiClient) {
        this.githubApiClient = githubApiClient;
    }

    @Override
    public GithubAccessTokenDTO tokenExchange(String code) {
        return githubApiClient.exchangeToken(
                githubClientId,
                githubClientSecret,
                code
        );
    }

    @Override
    public GithubAccessTokenDTO refreshToken(String refreshToken) {

        System.out.println("REFRESHING USER's TOKEN");
        System.out.println("REFRESH TOKEN: " + refreshToken);

        GithubAccessTokenDTO dto = githubApiClient.refreshToken(
                githubClientId,
                githubClientSecret,
                "refresh_token",
                refreshToken
        );

        System.out.println("NEW TOKEN: " + dto.refreshToken());

        return dto;
    }

    @Override
    public GithubProfileDTO queryAuthenticatedUser(GithubAccessToken token) {
        return githubApiClient.getUserProfile("Bearer " + token.getAccessToken());
    }

    @Override
    public List<GithubRepositoryDTO> getUserOwnedRepositories(GithubAccessToken token) {
        // prefer max page count so less api calls required
        return githubApiClient.getUserRepositories(
                "Bearer " + token.getAccessToken(),
                "owner",
                100,
                0
        );
    }

    @Override
    public List<GithubRepositoryDTO> getUserOwnedRepositories(GithubAccessToken token, int page) {
        return githubApiClient.getUserRepositories(
                "Bearer " + token.getAccessToken(),
                "owner",
                100,
                page
        );
    }

    @Override
    public String getRepositoryLink(GithubAccessToken token, long repositoryId) {
        GithubRepositoryDTO repo =  githubApiClient.getRepository(
                "Bearer " + token.getAccessToken(),
                repositoryId
        );
        return repo.url();
    }
}
