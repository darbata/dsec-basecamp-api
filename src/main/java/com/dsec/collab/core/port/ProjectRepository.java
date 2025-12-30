package com.dsec.collab.core.port;

import com.dsec.collab.core.domain.Project;

import java.util.UUID;

public interface ProjectRepository {
    public Project get(UUID projectId);
    public void save(Project project);
}
