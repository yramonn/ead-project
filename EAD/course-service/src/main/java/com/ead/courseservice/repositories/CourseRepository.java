package com.ead.courseservice.repositories;

import com.ead.courseservice.models.CourseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<CourseModel, UUID> {

    boolean existsByName(String name);
}
