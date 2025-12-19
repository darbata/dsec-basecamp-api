package com.dsec.collab.adaptor.repository.jpa;

import com.dsec.collab.core.domain.user.User;
import com.dsec.collab.core.port.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class JpaUserRepository implements UserRepository {

    private final JpaUserSchemaRepository jpaUserSchemaRepository;

    public JpaUserRepository(JpaUserSchemaRepository jpaUserSchemaRepository) {
        this.jpaUserSchemaRepository = jpaUserSchemaRepository;
    }

    @Override
    public User findById(UUID id) {
        System.out.println("finding by id no cap");
        UserSchema userSchema = jpaUserSchemaRepository.findById(id).orElse(null);
        if (userSchema == null) return null;
        return userSchemaToUser(userSchema);
    }

    @Override
    public User save(UUID id, User user) {
        return null;
    }

    private User userSchemaToUser(UserSchema userSchema) {
        return User.UserFactory(userSchema.getId(), userSchema.getEmail(), userSchema.getName());
    }

}
