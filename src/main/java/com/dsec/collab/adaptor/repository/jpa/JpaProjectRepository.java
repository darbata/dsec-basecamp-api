package com.dsec.collab.adaptor.repository.jpa;

import com.dsec.collab.core.domain.Project;
import com.dsec.collab.core.port.ProjectRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaProjectRepository implements ProjectRepository {

    private final JpaProjectSchemaRepository repo;

    public JpaProjectRepository(JpaProjectSchemaRepository jpaProjectSchemaRepository) {
        this.repo = jpaProjectSchemaRepository;
    }

    @Override
    public Project get(UUID projectId) {
        Optional<ProjectSchema> schema = this.repo.findById(projectId);
        return schema.map(this::toDomain).orElse(null);
    }

    @Override
    public Project save(Project project) {
        ProjectSchema schema = toEntity(project);
        assert schema != null;
        return toDomain(repo.save(schema));
    }

    @Override
    public List<Project> getAll() {
        return repo.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public List<Project> getAllFeatured() {
        return repo.findAllByFeaturedTrue().stream().map(this::toDomain).toList();
    }

    @Override
    public List<Project> getAllCommunity() {
        return repo.findAllByFeaturedFalse().stream().map(this::toDomain).toList();
    }

    @Override
    public void delete(UUID project) {
        repo.deleteById(project);
    }

    private Project toDomain(ProjectSchema projectSchema) {
        return null;
    }

    private ProjectSchema toEntity(Project project) {
        return null;
    }
}
