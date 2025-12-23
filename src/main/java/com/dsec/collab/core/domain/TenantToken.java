package com.dsec.collab.core.domain;

public abstract class TenantToken {
    private final String accessToken;
    private final String scope;
    private final String tokenType;

    public TenantToken(String accessToken, String scope, String tokenType) {
        this.accessToken = accessToken;
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
}
