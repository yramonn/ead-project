package com.ead.courseservice.services.impl;

import com.ead.courseservice.repositories.ModuleRepository;
import com.ead.courseservice.services.ModuleService;
import org.springframework.stereotype.Service;

@Service
public class ModuleServiceImpl implements ModuleService {

    final ModuleRepository moduleRepository;

    public ModuleServiceImpl(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }
}
