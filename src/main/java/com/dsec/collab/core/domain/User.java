package com.dsec.collab.core.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class User {
    private final UUID id;
    private String email;
    private String name;

    private GithubAccessToken githubAccessToken;
    private GithubProfile githubProfile;

    private User(UUID id, String email, String name, GithubAccessToken githubAccessToken, GithubProfile githubProfile) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.githubAccessToken = githubAccessToken;
        this.githubProfile = githubProfile;
    }

    public UUID getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGithubConnected() {
        return this.githubAccessToken != null;
    }

    public GithubAccessToken getGithubAccessToken() {
        return this.githubAccessToken;
    }

    public void setGithubAccessToken(GithubAccessToken githubAccessToken) {
        this.githubAccessToken = githubAccessToken;
    }

    public boolean hasValidToken() {
        if (githubAccessToken == null) return false;

        return (this.githubAccessToken.getAccessTokenExpiryDate().isAfter(LocalDateTime.now()));
    }

    public GithubProfile getGithubProfile() {
        return this.githubProfile;
    }

    public void setGithubProfile(GithubProfile githubProfile) {
        this.githubProfile = githubProfile;
    }

    public static User create(UUID id, String email, String name) {
        return new User(
                Objects.requireNonNull(id),
                Objects.requireNonNull(email),
                Objects.requireNonNull(name),
                null,
                null
        );
    }

    public static User load(
            UUID id,
            String email,
            String name,
            GithubAccessToken githubAccessToken,
            GithubProfile githubProfile
    ) {
        return new User(
                Objects.requireNonNull(id),
                Objects.requireNonNull(email),
                Objects.requireNonNull(name),
                githubAccessToken,
                githubProfile );
    }
}


