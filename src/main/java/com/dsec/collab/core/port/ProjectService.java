package com.dsec.collab.core.port;

import com.dsec.collab.core.domain.Project;
import com.dsec.collab.core.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectService implements ProjectApi {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TokenRefresherApi tokenRefresherApi;
    private final IGithubProxy proxy;

    public ProjectService(
            ProjectRepository projectRepository,
            UserRepository userRepository,
            TokenRefresherApi tokenRefresherApi,
            IGithubProxy proxy
    ) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.tokenRefresherApi = tokenRefresherApi;
        this.proxy = proxy;
    }

    @Override
    public Project getProject(UUID projectId) {
        return projectRepository.get(projectId);
    }

    @Override
    public Project createProject(UUID userId, long githubRepositoryId, String title, String description) {
        // need to ensure that repository link is taken from github api, don't trust users with posting links
        Optional<User> user = userRepository.findById(userId);
        tokenRefresherApi.validateToken(user.get());
        String repositoryLink = proxy.getRepositoryLink(user.get().getGithubAccessToken(),  githubRepositoryId);
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
    public Page<Project> getUserProjects(Pageable pageable) {
        return projectRepository.getAll(pageable);
    }

    @Override
    public Page<Project> getCommunityProjects(Pageable pageable) {
        return projectRepository.getAllCommunity(pageable);
    }

    @Override
    public Page<Project> getFeaturedProjects(Pageable pageable) {
        return projectRepository.getAllFeatured(pageable);
    }

}
