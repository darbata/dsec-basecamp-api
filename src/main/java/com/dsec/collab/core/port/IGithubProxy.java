package com.dsec.collab.core.port;

import com.dsec.collab.adaptor.http.GithubUserAccessToken;
import com.dsec.collab.adaptor.http.GithubUserProfile;

public interface IGithubProxy {
    GithubUserAccessToken tokenExchange(String code);
    GithubUserProfile queryAuthenticatedUser(String accessToken);
}
