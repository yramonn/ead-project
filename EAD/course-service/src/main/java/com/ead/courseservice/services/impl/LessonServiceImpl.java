package com.ead.courseservice.services.impl;

import com.ead.courseservice.repositories.LessonRepository;
import com.ead.courseservice.services.LessonService;
import org.springframework.stereotype.Service;

@Service
public class LessonServiceImpl implements LessonService {

    final LessonRepository lessonRepository;

    public LessonServiceImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }
}
