package com.dsec.collab.adaptor.http.controller;

import com.dsec.collab.adaptor.http.proxy.GithubCode;
import com.dsec.collab.core.port.UserApi;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class GithubController {

    private final UserApi userApi;

    public GithubController(UserApi userApi) {
        this.userApi = userApi;
    }


    @PostMapping("/connect_github")
    public HttpStatus connectGithub(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody GithubCode githubCode
    ) {
        UUID id = UUID.fromString(jwt.getClaimAsString("sub"));
        userApi.connectGithub(id, githubCode.getCode());
        return HttpStatus.CREATED;
    }

}