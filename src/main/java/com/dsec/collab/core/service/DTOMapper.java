package com.dsec.collab.core.service;

import com.dsec.collab.adaptor.http.GithubAccessTokenDTO;
import com.dsec.collab.adaptor.http.GithubProfileDTO;
import com.dsec.collab.adaptor.http.ProjectDTO;
import com.dsec.collab.adaptor.http.UserDTO;
import com.dsec.collab.core.domain.GithubAccessToken;
import com.dsec.collab.core.domain.GithubProfile;
import com.dsec.collab.core.domain.Project;
import com.dsec.collab.core.domain.User;
import com.dsec.collab.core.port.IDTOMapper;
import org.springframework.stereotype.Service;

@Service
public class DTOMapper implements IDTOMapper {
    @Override
    public UserDTO toDTO(User user) {
        GithubProfile profile = user.getGithubProfile();

        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.isGithubConnected(),
                profile != null ? profile.getId() : null,
                profile != null ? profile.getUsername() : null,
                profile != null ? profile.getUrl() : null,
                profile != null ? profile.getAvatarUrl() : null
        );
    }

    @Override
    public GithubProfile toEntity(GithubProfileDTO dto) {
        return GithubProfile.create(
                dto.githubId(),
                dto.githubUsername(),
                dto.githubUrl(),
                dto.githubAvatarUrl()
        );
    }

    @Override
    public GithubAccessToken toEntity(GithubAccessTokenDTO dto) {
        return GithubAccessToken.create(
                dto.accessToken(),
                dto.expiresIn(),
                dto.refreshToken(),
                dto.refreshTokenExpiresIn(),
                dto.scope(),
                dto.tokenType()
        );
    }

    @Override
    public ProjectDTO toDTO(Project project) {
        System.out.println("mapping project to dto");
        return new ProjectDTO(
                project.getId(),
                project.getOwnerId(),
                project.getGithubRepositoryId(),
                project.getRepositoryLink(),
                project.getTitle(),
                project.getDescription(),
                project.isFeatured()
        );
    }

    @Override
    public Project toEntity(ProjectDTO dto) {
        System.out.println("mapping dto to project");
        return Project.load(
            dto.id(),
            dto.ownerId(),
            dto.githubRepositoryId(),
            dto.repositoryLink(),
            dto.title(),
            dto.description(),
            dto.featured()
        );
    }
}