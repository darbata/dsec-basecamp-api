package com.dsec.collab.adaptor.http.controller;

import com.dsec.collab.adaptor.http.dto.UserDTO;
import com.dsec.collab.adaptor.http.dto.GithubRepositoryDTO;
import com.dsec.collab.core.port.UserApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserApi userApi;

    public UserController(UserApi userApi) {
        this.userApi = userApi;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal Jwt jwt) {
        // function to get the current user (id from JWT token) details from the db
        try {

            // get the user's id from the JWT token
            UUID id = UUID.fromString(jwt.getClaimAsString("sub"));
            String email = jwt.getClaimAsString("email");
            String name = jwt.getClaimAsString("name");

            // use the service to get the user from persistence
            UserDTO userDTO = userApi.getOrCreateUser(id, email, name);

            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/repos")
    public ResponseEntity<?> getUserGithubRepositories(@AuthenticationPrincipal Jwt jwt) {
        try {
            List<GithubRepositoryDTO> repos = userApi.getUserRepositories(UUID.fromString(jwt.getClaimAsString("sub")));
            return new ResponseEntity<>(repos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
