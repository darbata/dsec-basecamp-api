package com.dsec.collab.adaptor.http;

import com.dsec.collab.core.domain.TenantUserProfile;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GithubUserProfile extends TenantUserProfile {

    @JsonCreator
    public GithubUserProfile(
            @JsonProperty("id") long tenantId,
            @JsonProperty("login") String tenantUsername,
            @JsonProperty("url") String tenantUrl,
            @JsonProperty("avatar_url") String tenantAvatarUrl
    ) {
        super(tenantId, tenantUsername, tenantUrl, tenantAvatarUrl);
    }
}
