package com.dsec.collab.adaptor.http.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubRepositoryDTO(
        long id,
        String name,
        String fullName,
        String description,
        String url
) {
    @JsonCreator
    public GithubRepositoryDTO (
        @JsonProperty("id") long id,
        @JsonProperty("name") String name,
        @JsonProperty("full_name") String fullName,
        @JsonProperty("description") String description,
        @JsonProperty("url") String url
    ) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.description = description;
        this.url = url;
    }
}
