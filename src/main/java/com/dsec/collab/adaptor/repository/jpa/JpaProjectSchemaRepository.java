package com.dsec.collab.adaptor.repository.jpa;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaProjectSchemaRepository extends JpaRepository<ProjectSchema, UUID> {
    List<ProjectSchema> findAllByFeaturedTrue(Pageable pageable);
    List<ProjectSchema> findAllByFeaturedFalse(Pageable pageable);
    boolean existsByGithubRepositoryId(Long githubRepositoryId);
}
