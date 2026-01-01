package com.dsec.collab.core.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class GithubAccessToken {
    private final String accessToken; private final LocalDateTime accessTokenExpiryDate;
    private final String refreshToken;
    private final LocalDateTime refreshTokenExpiryDate;
    private final String scope;
    private final String tokenType;

    private GithubAccessToken(
            String accessToken,
            LocalDateTime accessTokenExpiryDate,
            String refreshToken,
            LocalDateTime refreshTokenExpiryDate,
            String scope,
            String tokenType
    ) {
        this.accessToken = accessToken;
        this.accessTokenExpiryDate = accessTokenExpiryDate;
        this.refreshToken = refreshToken;
        this.refreshTokenExpiryDate = refreshTokenExpiryDate;
        this.scope = scope;
        this.tokenType = tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public LocalDateTime getAccessTokenExpiryDate() {
        return accessTokenExpiryDate;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public LocalDateTime getRefreshTokenExpiryDate() {
        return refreshTokenExpiryDate;
    }

    public String getScope() {
        return scope;
    }

    public String getTokenType() {
        return tokenType;
    }

    // factory
    public static GithubAccessToken create(
            String accessToken,
            Integer expiresIn,
            String refreshToken,
            Integer refreshTokenExpiresIn,
            String scope,
            String tokenType
    ) {
        LocalDateTime now = LocalDateTime.now();

        // including a 10 minute buffer
        LocalDateTime accessTokenExpiryDate = now.plusSeconds(expiresIn).minusMinutes(10);
        LocalDateTime refreshTokenExpiryDate = now.plusSeconds(refreshTokenExpiresIn).minusMinutes(10);

        return new GithubAccessToken(
                Objects.requireNonNull(accessToken),
                Objects.requireNonNull(accessTokenExpiryDate),
                Objects.requireNonNull(refreshToken),
                Objects.requireNonNull(refreshTokenExpiryDate),
                Objects.requireNonNull(scope),
                Objects.requireNonNull(tokenType)
        );
    }

    public static GithubAccessToken load(
            String accessToken,
            LocalDateTime accessTokenExpiryDate,
            String refreshToken,
            LocalDateTime refreshTokenExpiryDate,
            String scope,
            String tokenType
    ) {
        return new GithubAccessToken(
                Objects.requireNonNull(accessToken),
                Objects.requireNonNull(accessTokenExpiryDate),
                Objects.requireNonNull(refreshToken),
                Objects.requireNonNull(refreshTokenExpiryDate),
                Objects.requireNonNull(scope),
                Objects.requireNonNull(tokenType)
        );
    }
}
