package com.dsec.collab.core.service;

import com.dsec.collab.core.domain.Project;
import com.dsec.collab.core.port.ProjectApi;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectService implements ProjectApi {

    @Override
    public Project getProject(UUID projectId) {
    }

    @Override
    public Project createProject(UUID userId, long githubRepositoryId, String title, String description) {
        return null;
    }

    @Override
    public Project updateProject(UUID userId, UUID projectId, String title, String description) {
        return null;
    }

    @Override
    public Project deleteProject(UUID userId, UUID projectId) {
        return null;
    }

    @Override
    public Project setProjectFeatured(UUID projectId) {
        return null;
    }

    @Override
    public Project setProjectCommunity(UUID projectId) {
        return null;
    }

    @Override
    public List<Project> getUserProjects(UUID userId) {
        return List.of();
    }

    @Override
    public List<Project> getCommunityProjects() {
        return List.of();
    }

    @Override
    public List<Project> getFeaturedProjects() {
        return List.of();
    }
}
