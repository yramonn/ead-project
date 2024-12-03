package com.ead.courseservice.dtos;

import jakarta.validation.constraints.NotBlank;

public record ModuleRecordDto(@NotBlank
                              String title,

                              @NotBlank
                              String description) {
}
