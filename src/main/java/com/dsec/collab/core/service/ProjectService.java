package com.dsec.collab.core.service;

import com.dsec.collab.adaptor.http.GithubRepositoryDTO; import com.dsec.collab.adaptor.http.ProjectDTO;
import com.dsec.collab.core.domain.Project;
import com.dsec.collab.core.domain.User;
import com.dsec.collab.core.exception.GithubAuthenticationException;
import com.dsec.collab.core.exception.GithubRepositoryIdUsedException;
import com.dsec.collab.core.port.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.naming.NoPermissionException;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectService implements ProjectApi {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TokenRefresherApi tokenRefresherApi;
    private final IGithubProxy proxy;
    private final IDTOMapper dtoMapper;

    public ProjectService(
            ProjectRepository projectRepository,
            UserRepository userRepository,
            TokenRefresherApi tokenRefresherApi,
            IGithubProxy proxy, IDTOMapper dtoMapper
    ) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.tokenRefresherApi = tokenRefresherApi;
        this.proxy = proxy;
        this.dtoMapper = dtoMapper;
    }

    @Override
    public ProjectDTO getProject(UUID projectId) {
        Project project = projectRepository.get(projectId);
        return dtoMapper.toDTO(project);
    }

    @Override
    public ProjectDTO createProject(UUID userId, long githubRepositoryId, String title, String description) {
        // does user exist?
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new IllegalCallerException("User with id " + userId + " not found");
        }

        // does user have a valid token?
        tokenRefresherApi.validateToken(user);
        if (!user.hasValidToken()) {
            throw new GithubAuthenticationException("user does not have a valid github access token");
        }

        // does project already exist with the same githubRepositoryId
        if (projectRepository.existsByGithubRepositoryId(githubRepositoryId)) {
            throw new GithubRepositoryIdUsedException("Project with repository id " + githubRepositoryId + " already exists");
        }


        String repositoryLink = proxy.getRepositoryLink(user.getGithubAccessToken(), githubRepositoryId);
        System.out.println("repository link: " + repositoryLink);
        Project project = Project.create(userId, githubRepositoryId, title, description, repositoryLink);
        return dtoMapper.toDTO(projectRepository.save(project));
    }

    @Override
    public ProjectDTO updateProject(UUID userId, UUID projectId, String title, String description) {
        Project project = projectRepository.get(projectId);
        project.setTitle(userId, title);
        project.setDescription(userId, description);
        return dtoMapper.toDTO(projectRepository.save(project));
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
    public ProjectDTO setProjectFeatured(UUID projectId) {
        Project project = projectRepository.get(projectId);
        project.setFeatured(true);
        return dtoMapper.toDTO(projectRepository.save(project));
    }

    @Override
    public void setProjectCommunity(UUID projectId) {
        Project project = projectRepository.get(projectId);
        project.setFeatured(false);
        projectRepository.save(project);
    }

    @Override
    public Page<ProjectDTO> getAllProjects(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return projectRepository.getAll(pageable).map(dtoMapper::toDTO);
    }

    @Override
    public Page<ProjectDTO> getCommunityProjects(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return projectRepository.getAllCommunity(pageable).map(dtoMapper::toDTO);

    }

    @Override
    public Page<ProjectDTO> getFeaturedProjects(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return projectRepository.getAllFeatured(pageable).map(dtoMapper::toDTO);
    }


}
