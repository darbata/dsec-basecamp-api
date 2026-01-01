package com.dsec.collab.adaptor.http.request;

public record CreateProjectRequestDTO(
    long githubRepositoryId,
    String title,
    String description
) { }

