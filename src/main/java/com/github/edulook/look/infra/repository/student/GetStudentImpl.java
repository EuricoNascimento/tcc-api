package com.github.edulook.look.infra.repository.student;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.github.edulook.look.core.model.Student;
import com.github.edulook.look.core.repository.student.GetStudent;
import com.github.edulook.look.infra.repository.student.mapper.ClassroomStudentAndCoreStudentMapper;
import com.google.api.services.classroom.Classroom;
import com.google.api.services.classroom.model.UserProfile;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GetStudentImpl implements GetStudent {

    private final Classroom classroom;
    private final ClassroomStudentAndCoreStudentMapper mapper;

    public GetStudentImpl(Classroom classroom, ClassroomStudentAndCoreStudentMapper mapper) {
        this.classroom = classroom;
        this.mapper = mapper;
    }

    @Override
    public Optional<Student> findStudentById(String studentId) {
        try{
            UserProfile userProfile = classroom
                .userProfiles()
                .get(studentId)
                .execute();
            
            return Optional.of(mapper.toModel(userProfile));
            
        } catch (IOException e) {
            e.printStackTrace();
            log.error("studentId: {}: {}", studentId, e);
            return Optional.empty();
        }
    }
}
