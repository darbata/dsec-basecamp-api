package com.dsec.collab.core.service;

import com.dsec.collab.core.domain.TenantToken;
import com.dsec.collab.core.domain.TenantUserProfile;
import com.dsec.collab.core.domain.User;
import com.dsec.collab.core.port.TenantProxy;
import com.dsec.collab.core.port.UserApi;
import com.dsec.collab.core.port.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserApi {

    private final UserRepository userRepository;
    private final TenantProxy tenantProxy;

    public UserService(UserRepository userRepository, TenantProxy tenantProxy) {
        this.userRepository = userRepository;
        this.tenantProxy = tenantProxy;
    }

    @Override
    public User getOrCreateUser(UUID id, String email, String name) {
       Optional<User> user = userRepository.findById(id);

       if (user.isPresent()) {
           System.out.println("Found user, returning them");
           return user.get();
       } else {
           System.out.println("User not found, creating new and returning");
           User newUser = User.create(id, email, name);
           return userRepository.save(newUser);
       }

    }

    @Override
    public User connectTenant(UUID id, String code) {
        try {

            TenantToken tenantToken = this.tenantProxy.tokenExchange(code);

            TenantUserProfile tenantUserProfile = this.tenantProxy.queryAuthenticatedUser(tenantToken);

            User user = userRepository.findById(id).orElse(null);

            assert user != null;

            user.setIsGithubConnected(true);
            user.setGithubId(tenantUserProfile.getTenantId());
            user.setGithubUser(tenantUserProfile.getTenantUsername());
            user.setGithubUrl(tenantUserProfile.getTenantUrl());
            user.setGithubAvatarUrl(tenantUserProfile.getTenantAvatarUrl());

            return userRepository.save(user);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public User putUser(String id, String email, String username) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return List.of();
    }



}