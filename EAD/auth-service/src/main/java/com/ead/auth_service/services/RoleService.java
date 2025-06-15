package com.ead.auth_service.services;

import com.ead.auth_service.enums.RoleType;
import com.ead.auth_service.models.RoleModel;

public interface RoleService {


    RoleModel findByRoleName(RoleType name);
}
