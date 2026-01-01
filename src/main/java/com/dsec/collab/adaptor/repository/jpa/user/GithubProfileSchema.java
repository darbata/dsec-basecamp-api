package com.dsec.collab.adaptor.repository.jpa.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="github_profiles")
public class GithubProfileSchema {
    @Id
    @OneToOne
    @MapsId
    @JoinColumn(name="user_id")
    private UserSchema userSchema;

    private long githubId;
    private String githubUsername;
    private String githubUrl;
    private String githubAvatarUrl;
}