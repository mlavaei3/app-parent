package com.lava.server.lesson.mapper;


import com.lava.server.lesson.model.entity.Lesson;
import com.lava.server.openapi.model.LessonDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LessonMapper {
    LessonDTO lessonToLessonDTO(Lesson lesson);
    Lesson lessonDTOToLesson(LessonDTO lessonDTO);
}

