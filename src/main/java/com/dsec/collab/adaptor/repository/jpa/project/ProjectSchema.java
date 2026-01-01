package com.dsec.collab.adaptor.repository.jpa.project;//    private final UUID id;

import com.dsec.collab.adaptor.repository.jpa.user.UserSchema;
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
@Table(name = "projects")
public class ProjectSchema {
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserSchema owner;

    private long githubRepositoryId;
    private String repositoryLink;
    private String title;
    private String description;
    private boolean featured;
}