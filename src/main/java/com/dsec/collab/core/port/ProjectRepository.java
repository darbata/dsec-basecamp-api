package com.dsec.collab.core.port;

import com.dsec.collab.core.domain.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProjectRepository {
    public Project get(UUID projectId);
    public Project save(Project project);
    public Page<Project> getAll(Pageable pageable);
    public Page<Project> getAllFeatured(Pageable pageable);
    public Page<Project> getAllCommunity(Pageable pageable);
    public void delete(UUID project);
    public boolean existsByGithubRepositoryId(Long githubRepositoryId);
}
