package com.ead.courseservice.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CourseUserRecordDto(@NotNull(message = "CourseId is mandatory")
                                  UUID courseId,
                                  UUID userId) {
}
