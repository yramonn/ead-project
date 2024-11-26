package com.ead.courseservice.services.impl;

import com.ead.courseservice.repositories.CourseRepository;
import com.ead.courseservice.services.CourseService;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
}
