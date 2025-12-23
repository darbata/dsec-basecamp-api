package com.dsec.collab.adaptor.repository.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserSchema {
    @Id
    private UUID id;
    private String email;
    private String name;

    private boolean githubConnected;
    private long githubId;
    private String githubUser;
    private String githubUrl;
    private String githubAvatarUrl;

    private boolean isAdmin;
    private boolean isMember;
}
