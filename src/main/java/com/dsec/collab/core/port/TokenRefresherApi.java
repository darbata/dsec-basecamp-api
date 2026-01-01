package com.dsec.collab.core.port;

import com.dsec.collab.core.domain.User;

import javax.naming.NoPermissionException;

public interface TokenRefresherApi {
    public void validateToken(User user);
}
