package com.dsec.collab.core.domain;

import java.util.Objects;
import java.util.UUID;

public class Project {
    private final UUID id;
    private final UUID ownerId;
    private final long githubRepositoryId;
    private final String repositoryLink;
    private String title;
    private String description;
    private boolean featured = false; // else "community project"

    private Project(UUID id, UUID ownerId, long githubRepositoryId, String repositoryLink, String title, String description) {
        this.id = id;
        this.githubRepositoryId = githubRepositoryId;
        this.ownerId = ownerId;
        this.repositoryLink = repositoryLink;
        this.title = title;
        this.description = description;
    }

    private Project(UUID id, UUID ownerId, long githubRepositoryId, String repositoryLink, String title, String description, boolean featured) {
        this.id = id;
        this.githubRepositoryId = githubRepositoryId;
        this.ownerId = ownerId;
        this.repositoryLink = repositoryLink;
        this.title = title;
        this.description = description;
        this.featured = featured;
    }

    public UUID getId() {
        return id;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public long getGithubRepositoryId() {
        return githubRepositoryId;
    }

    public String getRepositoryLink() {
        return repositoryLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(UUID userId, String title) {
        if (!this.ownerId.equals(userId)) throw new
                IllegalArgumentException("Only the owner of a project may update its details");

        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(UUID userId, String description) {
        if (!this.ownerId.equals(userId)) throw new
                IllegalArgumentException("Only the owner of a project may update its details");

        this.description = description;
    }

    public static Project create(UUID ownerId, long githubRepositoryId, String repositoryLink, String title, String description) {
        return new Project(
                UUID.randomUUID(),
                Objects.requireNonNull(ownerId),
                githubRepositoryId,
                Objects.requireNonNull(repositoryLink),
                Objects.requireNonNull(title),
                Objects.requireNonNull(description)
        );
    }

    public static Project load(UUID id, UUID ownerId, long githubRepositoryId, String repositoryLink, String title, String description, boolean featured) {
        return new Project(
                Objects.requireNonNull(id),
                Objects.requireNonNull(ownerId),
                githubRepositoryId,
                Objects.requireNonNull(repositoryLink),
                Objects.requireNonNull(title),
                Objects.requireNonNull(description),
                featured
        );
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }
}