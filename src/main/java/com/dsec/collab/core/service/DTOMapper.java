package com.dsec.collab.core.service;

import com.dsec.collab.adaptor.http.GithubAccessTokenDTO;
import com.dsec.collab.adaptor.http.GithubProfileDTO;
import com.dsec.collab.adaptor.http.UserDTO;
import com.dsec.collab.core.domain.GithubAccessToken;
import com.dsec.collab.core.domain.GithubProfile;
import com.dsec.collab.core.domain.User;
import com.dsec.collab.core.port.IDTOMapper;
import org.springframework.stereotype.Service;

@Service
public class DTOMapper implements IDTOMapper {
    @Override
    public UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.isGithubConnected(),
                user.getGithubProfile().getId(),
                user.getGithubProfile().getUsername(),
                user.getGithubProfile().getUrl(),
                user.getGithubProfile().getAvatarUrl()
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
}