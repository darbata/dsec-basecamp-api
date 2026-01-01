package com.dsec.collab.core.port;

import com.dsec.collab.adaptor.http.dto.ProjectDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ProjectApi {

    ProjectDTO getProject(UUID projectId);
    ProjectDTO createProject(UUID userId, long githubRepositoryId, String title, String description);
    ProjectDTO updateProject(UUID userId, UUID projectId, String title, String description);
    void deleteProject(UUID userId, UUID projectId);

    ProjectDTO setProjectFeatured(UUID projectId);
    void setProjectCommunity(UUID projectId);

    Page<ProjectDTO> getAllProjects(int page, int size);
    Page<ProjectDTO> getCommunityProjects(int page, int size);
    Page<ProjectDTO> getFeaturedProjects(int page, int size);

}
