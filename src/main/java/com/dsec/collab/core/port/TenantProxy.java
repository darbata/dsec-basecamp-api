package com.dsec.collab.core.port;

import com.dsec.collab.core.domain.TenantToken;
import com.dsec.collab.core.domain.TenantUserProfile;

public interface TenantProxy {
    TenantToken tokenExchange(String code);
    TenantUserProfile queryAuthenticatedUser(TenantToken tenantToken);
}
