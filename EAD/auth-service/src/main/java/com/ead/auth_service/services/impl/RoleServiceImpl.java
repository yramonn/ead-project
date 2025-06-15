package com.ead.auth_service.services.impl;

import com.ead.auth_service.repositories.RoleRepository;
import com.ead.auth_service.services.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
}
