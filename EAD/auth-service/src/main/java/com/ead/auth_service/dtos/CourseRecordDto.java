package com.ead.auth_service.dtos;

import com.ead.auth_service.enums.CourseLevel;
import com.ead.auth_service.enums.CourseStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

public record CourseRecordDto(UUID courseId,
                              String name,
                              String description,
                              CourseStatus courseStatus,
                              CourseLevel courseLevel,
                              UUID userInstructor) {
}
