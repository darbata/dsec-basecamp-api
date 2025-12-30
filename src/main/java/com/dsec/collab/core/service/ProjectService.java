package com.dsec.collab.core.service;

import com.dsec.collab.core.domain.Project;
import com.dsec.collab.core.port.IGithubProxy;
import com.dsec.collab.core.port.ProjectApi;
import com.dsec.collab.core.port.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectService implements ProjectApi {

    private final ProjectRepository projectRepository;
    private final IGithubProxy proxy;

    public ProjectService(ProjectRepository projectRepository, IGithubProxy proxy) {
        this.projectRepository = projectRepository;
        this.proxy = proxy;
    }

    @Override
    public Project getProject(UUID projectId) {
        return projectRepository.get(projectId);
    }

    @Override
    public Project createProject(UUID userId, long githubRepositoryId, String title, String description) {
        String repositoryLink = proxy.getRepositoryLink(githubRepositoryId);
        Project project = Project.create(userId, githubRepositoryId, title, description, repositoryLink);
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(UUID userId, UUID projectId, String title, String description) {
        Project project = projectRepository.get(projectId);
        project.setTitle(userId, title);
        project.setDescription(userId, description);
        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(UUID userId, UUID projectId) {
        Project project = projectRepository.get(projectId);

        if (project.getOwnerId().equals(userId)) {
            projectRepository.delete(projectId);
        } else {
            throw new IllegalCallerException("User of id: " + userId + " is not the owner of project of id: " + projectId + " and may not delete it.");
        }
    }

    @Override
    public Project setProjectFeatured(UUID projectId) {
        Project project = projectRepository.get(projectId);
        project.setFeatured(true);
        return projectRepository.save(project);
    }

    @Override
    public Project setProjectCommunity(UUID projectId) {
        Project project = projectRepository.get(projectId);
        project.setFeatured(false);
        return projectRepository.save(project);
    }

    @Override
    public List<Project> getUserProjects(UUID userId) {
        return projectRepository.getAll();
    }

    @Override
    public List<Project> getCommunityProjects() {
        return projectRepository.getAllCommunity();
    }

    @Override
    public List<Project> getFeaturedProjects() {
        return projectRepository.getAllFeatured();
    }
}
