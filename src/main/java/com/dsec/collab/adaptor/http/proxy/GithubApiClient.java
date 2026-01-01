package com.dsec.collab.adaptor.http.proxy;

import com.dsec.collab.adaptor.http.dto.GithubProfileDTO;
import com.dsec.collab.adaptor.http.dto.GithubRepositoryDTO;
import com.dsec.collab.adaptor.http.dto.GithubAccessTokenDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

@HttpExchange
@Service
public interface GithubApiClient {

    @PostExchange("https://github.com/login/oauth/access_token")
    GithubAccessTokenDTO exchangeToken(
            @RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret,
            @RequestParam("code") String code
    );

    @PostExchange("https://github.com/login/oauth/access_token")
    GithubAccessTokenDTO refreshToken(
            @RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret,
            @RequestParam("grant_type") String grantType,
            @RequestParam("refresh_token") String refreshToken
    );

    @GetExchange("https://api.github.com/user")
    GithubProfileDTO getUserProfile(
            @RequestHeader("Authorization") String token
    );

    @GetExchange("https://api.github.com/user/repos")
    List<GithubRepositoryDTO> getUserRepositories(
            @RequestHeader("Authorization") String token,
            @RequestParam("affiliation") String affiliation,
            @RequestParam("per_page") int perPage,
            @RequestParam("page") int page
    );

    @GetExchange("https://api.github.com/repositories/{id}")
    GithubRepositoryDTO getRepository(
            @RequestHeader("Authorization") String token,
            @PathVariable("id") Long id
    );

}