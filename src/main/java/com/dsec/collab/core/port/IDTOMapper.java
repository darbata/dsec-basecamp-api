package com.dsec.collab.core.port;

import com.dsec.collab.adaptor.http.GithubAccessTokenDTO;
import com.dsec.collab.adaptor.http.GithubProfileDTO;
import com.dsec.collab.adaptor.http.ProjectDTO;
import com.dsec.collab.adaptor.http.UserDTO;
import com.dsec.collab.core.domain.GithubAccessToken;
import com.dsec.collab.core.domain.GithubProfile;
import com.dsec.collab.core.domain.Project;
import com.dsec.collab.core.domain.User;

public interface IDTOMapper {
    UserDTO toDTO(User user);
    GithubProfile toEntity(GithubProfileDTO dto);
    GithubAccessToken toEntity(GithubAccessTokenDTO dto);
    ProjectDTO toDTO(Project project);
    Project toEntity(ProjectDTO dto);
}
