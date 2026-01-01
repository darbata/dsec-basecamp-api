package com.dsec.collab.adaptor.repository.jpa.user;

import com.dsec.collab.core.domain.GithubAccessToken;
import com.dsec.collab.core.domain.GithubProfile;
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
    public User save(User user) {
        UserSchema userSchema = userToUserSchema(user);
        UserSchema savedSchema = jpaUserSchemaRepository.save(userSchema);
        return userSchemaToUser(savedSchema);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaUserSchemaRepository.findById(id).map(this::userSchemaToUser);
    }

    private User userSchemaToUser(UserSchema userSchema) {
        if (userSchema == null) return null;

        User user = User.load(
                userSchema.getId(),
                userSchema.getEmail(),
                userSchema.getName(),
                null,
                null
        );

        if (userSchema.getGithubProfileSchema() != null) {
            GithubProfileSchema githubProfileSchema = userSchema.getGithubProfileSchema();

            GithubProfile githubProfile = GithubProfile.load(
                    githubProfileSchema.getGithubId(),
                    githubProfileSchema.getGithubUsername(),
                    githubProfileSchema.getGithubUrl(),
                    githubProfileSchema.getGithubAvatarUrl()
            );

            user.setGithubProfile(githubProfile);
        }

        if (userSchema.getGithubAccessTokenSchema() != null) {
            GithubAccessTokenSchema githubAccessTokenSchema = userSchema.getGithubAccessTokenSchema();

            GithubAccessToken githubAccessToken = GithubAccessToken.load(
                    githubAccessTokenSchema.getAccessToken(),
                    githubAccessTokenSchema.getAccessTokenExpiryDate(),
                    githubAccessTokenSchema.getRefreshToken(),
                    githubAccessTokenSchema.getRefreshTokenExpiryDate(),
                    githubAccessTokenSchema.getScope(),
                    githubAccessTokenSchema.getTokenType()
            );

            user.setGithubAccessToken(githubAccessToken);
        }

        return user;
    }

    private UserSchema userToUserSchema(User user) {
        if (user == null) return null;

        UserSchema userSchema = new UserSchema();
        userSchema.setId(user.getId());
        userSchema.setEmail(user.getEmail());
        userSchema.setName(user.getName());

        if (user.getGithubProfile() != null) {
            GithubProfileSchema githubProfileSchema = new GithubProfileSchema();

            githubProfileSchema.setGithubId(user.getGithubProfile().getId());
            githubProfileSchema.setGithubUsername(user.getGithubProfile().getUsername());
            githubProfileSchema.setGithubUrl(user.getGithubProfile().getUrl());
            githubProfileSchema.setGithubAvatarUrl(user.getGithubProfile().getAvatarUrl());


            githubProfileSchema.setUserSchema(userSchema);
            userSchema.setGithubProfileSchema(githubProfileSchema);
        }

        if  (user.getGithubAccessToken() != null) {
            GithubAccessTokenSchema githubAccessTokenSchema = new GithubAccessTokenSchema();

            githubAccessTokenSchema.setAccessToken(user.getGithubAccessToken().getAccessToken());
            githubAccessTokenSchema.setAccessTokenExpiryDate(user.getGithubAccessToken().getAccessTokenExpiryDate());
            githubAccessTokenSchema.setRefreshToken(user.getGithubAccessToken().getRefreshToken());
            githubAccessTokenSchema.setRefreshTokenExpiryDate(user.getGithubAccessToken().getRefreshTokenExpiryDate());
            githubAccessTokenSchema.setScope(user.getGithubAccessToken().getScope());
            githubAccessTokenSchema.setTokenType(user.getGithubAccessToken().getTokenType());

            githubAccessTokenSchema.setUserSchema(userSchema);
            userSchema.setGithubAccessTokenSchema(githubAccessTokenSchema);
        }

        return userSchema;
    }

}
