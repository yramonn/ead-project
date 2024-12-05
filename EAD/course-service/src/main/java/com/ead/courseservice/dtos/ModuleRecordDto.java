package com.ead.courseservice.dtos;

import jakarta.validation.constraints.NotBlank;

public record ModuleRecordDto(@NotBlank(message = "Title is mandatory")
                              String title,

                              @NotBlank(message = "Description is mandatory")
                              String description) {
}
