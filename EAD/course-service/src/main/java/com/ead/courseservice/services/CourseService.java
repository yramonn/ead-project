package com.ead.courseservice.services;

import com.ead.courseservice.dtos.CourseRecordDto;
import com.ead.courseservice.models.CourseModel;
import jakarta.validation.Valid;

public interface CourseService {

    void delete(CourseModel courseModel);

    CourseModel save(@Valid CourseRecordDto courseRecordDto);

    boolean existsByName(String name);
}
