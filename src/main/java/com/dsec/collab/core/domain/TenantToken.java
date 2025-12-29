package com.dsec.collab.core.domain;

public abstract class TenantToken {
    private final String accessToken;
    private final Integer expiresIn;
    private final String refreshToken;
    private final Integer refreshTokenExpiresIn;
    private final String scope;
    private final String tokenType;

    public TenantToken(String accessToken, Integer expiresIn, String refreshToken, Integer refreshTokenExpiresIn, String scope, String tokenType) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
        this.scope = scope;
        this.tokenType = tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getScope() {
        return scope;
    }

    public String getTokenType() {
        return tokenType;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Integer getRefreshTokenExpiresIn() {
        return refreshTokenExpiresIn;
    }
}
