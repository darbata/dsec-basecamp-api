package com.dsec.collab.core.port;

import com.dsec.collab.core.domain.Project;

import java.util.List;
import java.util.UUID;

public interface ProjectApi {

    Project getProject(UUID projectId);
    Project createProject(UUID userId, long githubRepositoryId, String title, String description);
    Project updateProject(UUID userId, UUID projectId, String title, String description);
    void deleteProject(UUID userId, UUID projectId);

    Project setProjectFeatured(UUID projectId);
    Project setProjectCommunity(UUID projectId);

    List<Project> getUserProjects(UUID userId);
    List<Project> getCommunityProjects();
    List<Project> getFeaturedProjects();

}
