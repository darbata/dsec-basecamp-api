package com.dsec.collab.core.domain;

import java.util.UUID;

public class User {
    private final UUID id;
    private String email;
    private String name;

    private boolean githubConnected;
    private long githubId;
    private String githubUser;
    private String githubUrl;
    private String githubAvatarUrl;

    private boolean isAdmin;
    private boolean isMember;

    private User(UUID id, String email, String name, boolean githubConnected, long githubId, String githubUser, String githubUrl, String githubAvatarUrl, boolean isModerator, boolean isMember) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.githubConnected = githubConnected;
        this.githubId = githubId;
        this.githubUser = githubUser;
        this.githubUrl = githubUrl;
        this.githubAvatarUrl = githubAvatarUrl;
        this.isAdmin = isModerator;
        this.isMember = isMember;
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

    public void setName() {
        this.name = name;
    }

    public boolean isGithubConnected() {
        return githubConnected;
    }

    public void setIsGithubConnected(boolean githubConnected) {
        this.githubConnected = githubConnected;
    }

    public long getGithubId() {
        return githubId;
    }

    public void setGithubId(long githubId) {
        this.githubId = githubId;
    }

    public String getGithubUser() {
        return githubUser;
    }

    public void setGithubUser(String githubUser) {
        this.githubUser = githubUser;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public String getGithubAvatarUrl() {
        return githubAvatarUrl;
    }

    public void setGithubAvatarUrl(String githubAvatarUrl) {
        this.githubAvatarUrl = githubAvatarUrl;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isMember() {
        return isMember;
    }

    public void setIsMember(boolean member) {
        isMember = member;
    }

    public static User create(UUID id, String email, String name) {
        return new User(
            id,
            email,
            name,
            false,
            0,
            null,
            null,
            null,
            false,
            false
        );
    }

    public static User create(
        UUID id, String email, String name, boolean githubConnected,
        long githubId, String githubUser, String githubUrl, String
        githubAvatarUrl, boolean isModerator, boolean isMember
    ) {
        return new User(
            id, email, name, githubConnected, githubId, githubUser, githubUrl, githubAvatarUrl, isModerator, isMember
        );
    }

}