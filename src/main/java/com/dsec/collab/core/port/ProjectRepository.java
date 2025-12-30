package com.dsec.collab.core.port;

import com.dsec.collab.core.domain.Project;

import java.util.List;
import java.util.UUID;

public interface ProjectRepository {
    public Project get(UUID projectId);
    public Project save(Project project);
    public List<Project> getAll();
    public List<Project> getAllFeatured();
    public List<Project> getAllCommunity();
    public void delete(UUID project);
}
