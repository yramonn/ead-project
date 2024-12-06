package com.ead.courseservice.services;

import com.ead.courseservice.dtos.CourseRecordDto;
import com.ead.courseservice.models.CourseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import java.util.Optional;
import java.util.UUID;

public interface CourseService {

    void delete(CourseModel courseModel);

    CourseModel save(CourseRecordDto courseRecordDto);

    boolean existsByName(String name);

    Page<CourseModel> findAll(Specification<CourseModel> spec, Pageable pageable);

    Optional<CourseModel> findById(UUID courseId);

    CourseModel update(CourseRecordDto courseRecordDto, CourseModel courseModel);
}
