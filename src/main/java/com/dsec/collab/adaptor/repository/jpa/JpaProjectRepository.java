package com.dsec.collab.adaptor.repository.jpa;

import com.dsec.collab.core.domain.Project;
import com.dsec.collab.core.port.ProjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaProjectRepository implements ProjectRepository {

    private final JpaProjectSchemaRepository repo;
    private final JpaUserSchemaRepository jpaUserSchemaRepository;

    public JpaProjectRepository(JpaProjectSchemaRepository jpaProjectSchemaRepository, JpaUserSchemaRepository jpaUserSchemaRepository) {
        this.repo = jpaProjectSchemaRepository;
        this.jpaUserSchemaRepository = jpaUserSchemaRepository;
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
    public Page<Project> getAll(Pageable pageable) {
        List<Project> projects = repo.findAll(pageable).stream().map(this::toDomain).toList();
        return new PageImpl<>(projects);
    }

    @Override
    public Page<Project> getAllFeatured(Pageable pageable) {
        List<Project> projects = repo.findAllByFeaturedTrue(pageable).stream().map(this::toDomain).toList();
        return new PageImpl<>(projects);
    }

    @Override
    public Page<Project> getAllCommunity(Pageable pageable) {
        List<Project> projects = repo.findAllByFeaturedFalse(pageable).stream().map(this::toDomain).toList();
        return new PageImpl<>(projects);
    }

    @Override
    public void delete(UUID project) {
        repo.deleteById(project);
    }

    @Override
    public boolean existsByGithubRepositoryId(Long githubRepositoryId) {
        return repo.existsByGithubRepositoryId(githubRepositoryId);
    }

    private Project toDomain(ProjectSchema projectSchema) {
        return Project.load(
                projectSchema.getId(),
                projectSchema.getOwner().getId(),
                projectSchema.getGithubRepositoryId(),
                projectSchema.getRepositoryLink(),
                projectSchema.getTitle(),
                projectSchema.getDescription(),
                projectSchema.isFeatured()
        );
    }

    private ProjectSchema toEntity(Project project) {

        UserSchema userSchema = jpaUserSchemaRepository.findById(project.getOwnerId())
                .orElseThrow(() -> new RuntimeException("User not found for ID: " + project.getOwnerId()));

        return new ProjectSchema(
                project.getId(),
                userSchema,
                project.getGithubRepositoryId(),
                project.getRepositoryLink(),
                project.getTitle(),
                project.getDescription(),
                project.isFeatured()
        );
    }
}
