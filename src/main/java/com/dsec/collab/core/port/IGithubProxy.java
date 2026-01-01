package com.dsec.collab.core.port;

import com.dsec.collab.adaptor.http.dto.GithubAccessTokenDTO;
import com.dsec.collab.adaptor.http.dto.GithubProfileDTO;
import com.dsec.collab.adaptor.http.dto.GithubRepositoryDTO;
import com.dsec.collab.core.domain.GithubAccessToken;

import java.util.List;

public interface IGithubProxy {
    GithubAccessTokenDTO tokenExchange(String code);
    GithubAccessTokenDTO refreshToken(String refreshToken);
    GithubProfileDTO queryAuthenticatedUser(GithubAccessToken token);
    List<GithubRepositoryDTO> getUserOwnedRepositories(GithubAccessToken token); // default
    List<GithubRepositoryDTO> getUserOwnedRepositories(GithubAccessToken token, int page); // if a page is requested
    String getRepositoryLink(GithubAccessToken token, long repositoryId);
}
