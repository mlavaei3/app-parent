package com.lava.server.lesson;


import com.lava.server.openapi.model.LessonDTO;

public interface LessonInternalAPI {
    LessonDTO getLessonById(String id);

}
