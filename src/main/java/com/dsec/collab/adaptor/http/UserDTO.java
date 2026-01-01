package com.dsec.collab.adaptor.http;

import java.util.UUID;

public record UserDTO(
    UUID id,
    String email,
    String name,
    boolean githubConnected,
    Long githubId,
    String githubUserName,
    String githubUrl,
    String githubAvatarUrl
) {}
