package com.dsec.collab.adaptor.repository.jpa.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaUserSchemaRepository extends JpaRepository<UserSchema, UUID> {}