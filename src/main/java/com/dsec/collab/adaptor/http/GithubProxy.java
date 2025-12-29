package com.dsec.collab.adaptor.http;

import com.dsec.collab.core.domain.TenantToken;
import com.dsec.collab.core.port.TenantProxy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Service
public class GithubProxy implements TenantProxy {


    @Value("${spring.security.oauth2.client.github.client-id}")
    private String githubClientId;

    @Value("${spring.security.oauth2.client.github.client-secret}")
    private String githubClientSecret;

    private final RestTemplate restTemplate;

    public GithubProxy() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public GithubToken tokenExchange(String code) {
        // github url to exchange code for token
        System.out.println("GithubProxy token exchange");
        System.out.println(code);
        URI uri = URI.create("https://github.com/login/oauth/access_token");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("client_id", githubClientId);
        parameters.put("client_secret", githubClientSecret);
        parameters.put("code", code);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(parameters, headers);

        GithubToken githubToken = restTemplate.postForObject(uri, request, GithubToken.class);

        System.out.println("inside github proxy");
        System.out.println(githubToken.getAccessToken());
        System.out.println(githubToken.getRefreshToken());

        return githubToken;
    }

    @Override
    public GithubUserProfile queryAuthenticatedUser(TenantToken token) {
        URI uri = URI.create("https://api.github.com/user");

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token.getAccessToken());
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                Map.class
        );

        return restTemplate.exchange(uri, HttpMethod.GET, entity, GithubUserProfile.class).getBody();
    }

}
