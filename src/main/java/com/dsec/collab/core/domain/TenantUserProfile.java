package com.dsec.collab.core.domain;

public abstract class TenantUserProfile {
    private final long tenantId;
    private final String tenantUsername;
    private final String tenantUrl;
    private final String tenantAvatarUrl;

    public TenantUserProfile(long tenantId, String tenantUsername, String tenantUrl, String tenantAvatarUrl) {
        this.tenantId = tenantId;
        this.tenantUsername = tenantUsername;
        this.tenantUrl = tenantUrl;
        this.tenantAvatarUrl = tenantAvatarUrl;
    }

    public long getTenantId() {
        return tenantId;
    }

    public String getTenantUsername() {
        return tenantUsername;
    }

    public String getTenantUrl() {
        return tenantUrl;
    }

    public String getTenantAvatarUrl() {
        return tenantAvatarUrl;
    }
}
