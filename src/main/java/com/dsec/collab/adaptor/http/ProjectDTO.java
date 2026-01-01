package com.dsec.collab.adaptor.http;

import java.util.UUID;

public record ProjectDTO(
        UUID id,
        UUID ownerId,
        long githubRepositoryId,
        String repositoryLink,
        String title,
        String description,
        boolean featured
) {}