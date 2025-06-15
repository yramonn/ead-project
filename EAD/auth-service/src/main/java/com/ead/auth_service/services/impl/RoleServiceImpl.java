package com.ead.auth_service.services.impl;

import com.ead.auth_service.enums.RoleType;
import com.ead.auth_service.models.RoleModel;
import com.ead.auth_service.repositories.RoleRepository;
import com.ead.auth_service.services.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleModel findByRoleName(RoleType roleType) {
        return roleRepository.findByRoleName(roleType)
                .orElseThrow(() -> new RuntimeException("Error: Role not found"));
    }
}
