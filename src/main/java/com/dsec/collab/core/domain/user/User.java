package com.dsec.collab.core.domain.user;

import java.util.UUID;

public class User {
    private final UUID id;
    private final String email;
    private final String name;

    private User(UUID id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public UUID getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getName() {
        return this.name;
    }

    public static User UserFactory(UUID id, String email, String name) {
        return new User(id, email, name);
    }
}