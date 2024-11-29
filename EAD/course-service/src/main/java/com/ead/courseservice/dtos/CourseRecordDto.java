package com.ead.courseservice.dtos;

import com.ead.courseservice.enums.CourseLevel;
import com.ead.courseservice.enums.CourseStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CourseRecordDto(@NotBlank
                              String name,

                              @NotBlank
                              String description,

                              @NotNull
                              CourseStatus courseStatus,

                              @NotNull
                              CourseLevel courseLevel,

                              @NotNull
                              UUID userInstructor,

                              String imageUrl) {
}
