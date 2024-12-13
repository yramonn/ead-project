package com.ead.courseservice.services.impl;

import com.ead.courseservice.repositories.CourseUserModelRepository;
import com.ead.courseservice.services.CourseUserModelService;
import org.springframework.stereotype.Service;

@Service
public class CourseUserModelImpl implements CourseUserModelService {

    final CourseUserModelRepository courseUserModelRepository;

    public CourseUserModelImpl(CourseUserModelRepository courseUserModelRepository) {
        this.courseUserModelRepository = courseUserModelRepository;
    }
}
