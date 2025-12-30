package com.dsec.collab.adaptor.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaProjectSchemaRepository extends JpaRepository<ProjectSchema, UUID> {}
