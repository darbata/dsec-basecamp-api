package com.dsec.collab.adaptor.http;

public record CreateProjectRequestDTO(
    long githubRepositoryId,
    String title,
    String description
) { }

