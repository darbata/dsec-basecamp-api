package com.dsec.collab.adaptor.http;

import com.dsec.collab.core.domain.TenantToken;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GithubToken extends TenantToken {
    @JsonCreator
    public GithubToken(
            @JsonProperty("access_token") String accessToken,
            @JsonProperty("expires_in") Integer expiresIn,
            @JsonProperty("refresh_token") String refreshToken,
            @JsonProperty("refresh_token_expires_in") Integer refreshTokenExpiresIn,
            @JsonProperty("scope") String scope,
            @JsonProperty("token_type") String tokenType
    ) {
        super(accessToken, expiresIn, refreshToken, refreshTokenExpiresIn, scope, tokenType);
    }
}
