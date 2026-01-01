package com.dsec.collab.adaptor.http.controller;

import com.dsec.collab.adaptor.http.dto.ProjectDTO;
import com.dsec.collab.adaptor.http.request.UpdateProjectRequestDTO;
import com.dsec.collab.adaptor.http.request.CreateProjectRequestDTO;
import com.dsec.collab.core.exception.GithubAuthenticationException;
import com.dsec.collab.core.exception.GithubRepositoryIdUsedException;
import com.dsec.collab.core.port.ProjectApi;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectApi projectApi;

    public ProjectController(ProjectApi projectApi) {
        this.projectApi = projectApi;
    }

    @PostMapping("/")
    public ResponseEntity<?> createProject(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody CreateProjectRequestDTO body
    ) {
        UUID userId = UUID.fromString(jwt.getClaimAsString("sub"));
        try {
            projectApi.createProject(
                    userId,
                    body.githubRepositoryId(),
                    body.title(),
                    body.description()
            );
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (GithubAuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        } catch (GithubRepositoryIdUsedException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllProjects(
            @RequestParam(name="page", defaultValue="0") int pageNum,
            @RequestParam(name="page_size", defaultValue="50") int pageSize
    ) {
        try {
            Page<ProjectDTO> page = projectApi.getAllProjects(pageNum, pageSize);
            return new ResponseEntity<>(page, HttpStatus.OK);
        } catch (GithubAuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/featured")
    public ResponseEntity<?> getFeaturedProjects(
            @RequestParam(name="page", defaultValue="0") int pageNum,
            @RequestParam(name="page_size", defaultValue="50") int pageSize
    ) {
        try {
            Page<ProjectDTO> page = projectApi.getFeaturedProjects(pageNum, pageSize);
            return new ResponseEntity<>(page, HttpStatus.OK);
        } catch (GithubAuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/community")
    public ResponseEntity<?> getCommunityProjects(
            @RequestParam(name="page", defaultValue="0") int pageNum,
            @RequestParam(name="page_size", defaultValue="50") int pageSize
    ) {
        try {
            Page<ProjectDTO> page = projectApi.getCommunityProjects(pageNum, pageSize);
            return new ResponseEntity<>(page, HttpStatus.OK);
        } catch (GithubAuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProject(
            @PathVariable UUID id
    ) {
        try {
            ProjectDTO dto = projectApi.getProject(id);
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .body(Map.of("project", dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID id
    ) {
        try {
            UUID userId = UUID.fromString(jwt.getClaimAsString("sub"));
            projectApi.deleteProject(userId, id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID id,
            @RequestBody UpdateProjectRequestDTO requestDTO
    ) {
        try {
            UUID userId = UUID.fromString(jwt.getClaimAsString("sub"));

            projectApi.updateProject(
                        userId,
                        id,
                        requestDTO.title(),
                        requestDTO.description()
                    );

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}/featured")
    public ResponseEntity<?> setProjectFeatured(
            @PathVariable UUID id
    ) {
        try {
            projectApi.setProjectFeatured(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}/community")
    public ResponseEntity<?> setProjectCommunity(
            @PathVariable UUID id
    ) {
        try {
            projectApi.setProjectCommunity(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
