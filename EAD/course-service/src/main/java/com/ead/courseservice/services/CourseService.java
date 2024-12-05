package com.ead.courseservice.services;

import com.ead.courseservice.dtos.CourseRecordDto;
import com.ead.courseservice.models.CourseModel;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseService {

    void delete(CourseModel courseModel);

    CourseModel save(CourseRecordDto courseRecordDto);

    boolean existsByName(String name);

    List<CourseModel> findAll();

    Optional<CourseModel> findById(UUID courseId);

    CourseModel update(CourseRecordDto courseRecordDto, CourseModel courseModel);
}
