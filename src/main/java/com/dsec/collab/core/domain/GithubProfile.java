package com.dsec.collab.core.domain;

import java.util.Objects;

public class GithubProfile {
    private final long id;
    private final String username;
    private final String url;
    private final String avatarUrl;

    private GithubProfile(long id, String username, String url, String avatarUrl) {
        this.id = id;
        this.username = username;
        this.url = url;
        this.avatarUrl = avatarUrl;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getUrl() {
        return url;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public static GithubProfile create(long id, String username, String url, String avatarUrl) {
        return new GithubProfile(
                id,
                Objects.requireNonNull(username),
                Objects.requireNonNull(url),
                Objects.requireNonNull(avatarUrl)
        );
    }

    public static GithubProfile load(long id, String username, String url, String avatarUrl) {
        return new GithubProfile(
                id,
                Objects.requireNonNull(username),
                Objects.requireNonNull(url),
                Objects.requireNonNull(avatarUrl)
        );
    }
}
