package com.ead.auth_service.repositories;

import com.ead.auth_service.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleModel, UUID> {
}
