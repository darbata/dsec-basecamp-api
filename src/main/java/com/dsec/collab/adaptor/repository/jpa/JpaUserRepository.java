package com.dsec.collab.adaptor.repository.jpa;

import com.dsec.collab.core.domain.User;
import com.dsec.collab.core.port.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaUserRepository implements UserRepository {

    private final JpaUserSchemaRepository jpaUserSchemaRepository;

    public JpaUserRepository(JpaUserSchemaRepository jpaUserSchemaRepository) {
        this.jpaUserSchemaRepository = jpaUserSchemaRepository;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaUserSchemaRepository.findById(id).map(this::userSchemaToUser);
    }

    @Override
    public User save(User user) {
        UserSchema userSchema = userToUserSchema(user);
        UserSchema savedSchema = jpaUserSchemaRepository.save(userSchema);
        return userSchemaToUser(savedSchema);
    }

    private User userSchemaToUser(UserSchema userSchema) {
        return User.create(
                userSchema.getId(),
                userSchema.getEmail(),
                userSchema.getName(),
                userSchema.isGithubConnected(),
                userSchema.getGithubId(),
                userSchema.getGithubUser(),
                userSchema.getGithubUrl(),
                userSchema.getGithubAvatarUrl(),
                userSchema.isAdmin(),
                userSchema.isMember()
        );
    }

    private UserSchema userToUserSchema(User user) {
        return new UserSchema(
            user.getId(),
            user.getEmail(),
            user.getName(),
            user.isGithubConnected(),
            user.getGithubId(),
            user.getGithubUser(),
            user.getGithubUrl(),
            user.getGithubAvatarUrl(),
            user.isAdmin(),
            user.isMember()
        );
    }

}
