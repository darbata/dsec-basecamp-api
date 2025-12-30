package com.dsec.collab.adaptor.repository.jpa;

public class JpaProjectRepository  {

    private final JpaProjectSchemaRepository jpaProjectSchemaRepository;

    public JpaProjectRepository(JpaProjectSchemaRepository jpaProjectSchemaRepository) {
        this.jpaProjectSchemaRepository = jpaProjectSchemaRepository;
    }


}
