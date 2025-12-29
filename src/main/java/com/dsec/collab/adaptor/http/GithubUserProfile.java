package com.dsec.collab.adaptor.http;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubUserProfile(long githubId, String githubUsername, String githubUrl, String githubAvatarUrl) {

    @JsonCreator
    public GithubUserProfile(
            @JsonProperty("id") long githubId,
            @JsonProperty("login") String githubUsername,
            @JsonProperty("url") String githubUrl,
            @JsonProperty("avatar_url") String githubAvatarUrl
    ) {
        this.githubId = githubId;
        this.githubUsername = githubUsername;
        this.githubUrl = githubUrl;
        this.githubAvatarUrl = githubAvatarUrl;
    }
}
