package com.dsec.collab.adaptor.repository.jpa.user;

import jakarta.persistence.*;
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

    @OneToOne(mappedBy = "userSchema", cascade = CascadeType.ALL, orphanRemoval = true)
    private GithubProfileSchema githubProfileSchema;

    @OneToOne(mappedBy = "userSchema", cascade = CascadeType.ALL, orphanRemoval = true)
    private GithubAccessTokenSchema githubAccessTokenSchema;
}
