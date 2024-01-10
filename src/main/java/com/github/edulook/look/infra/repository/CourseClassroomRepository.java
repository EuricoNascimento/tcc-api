package com.github.edulook.look.infra.repository;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.repository.CourseRepository;
import com.github.edulook.look.core.repository.course.GetCourse;
import com.github.edulook.look.core.repository.course.GetCourseAnnouncement;
import com.github.edulook.look.core.repository.course.GetCourseWork;
import com.github.edulook.look.core.repository.course.GetCourseWorkMaterial;
import com.github.edulook.look.infra.repository.firestore.CourseFirestoreRepository;
import com.github.edulook.look.infra.repository.firestore.WorkMaterialFirestoreRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("CourseClassroomRepository::Class")
public class CourseClassroomRepository implements CourseRepository {

    private final GetCourse getCourseClassroom;
    private final CourseFirestoreRepository courseFirestoreRepository;
    private final GetCourseWorkMaterial getCourseWorkMaterialClassroom;
    private final GetCourseWork getCourseWorkClassroom;
    private final GetCourseAnnouncement getCourseAnnouncementClassroom;
    private final WorkMaterialFirestoreRepository workMaterialFirestoreRepository;

    public CourseClassroomRepository(@Qualifier("GetCourseClassroom::Class") GetCourse getCourseClassroom,
                                     CourseFirestoreRepository courseFirestoreRepository, @Qualifier("GetCourseWorkMaterialClassroom::Class") GetCourseWorkMaterial getCourseWorkMaterialClassroom,
                                     @Qualifier("GetCourseWorkClassroom::Class") GetCourseWork getCourseWorkClassroom,
                                     @Qualifier("GetCourseAnnouncementClassroom::Class") GetCourseAnnouncement getCourseAnnouncementClassroom, WorkMaterialFirestoreRepository workMaterialFirestoreRepository) {
        this.getCourseClassroom = getCourseClassroom;
        this.courseFirestoreRepository = courseFirestoreRepository;
        this.getCourseWorkMaterialClassroom = getCourseWorkMaterialClassroom;
        this.getCourseWorkClassroom = getCourseWorkClassroom;
        this.getCourseAnnouncementClassroom = getCourseAnnouncementClassroom;
        this.workMaterialFirestoreRepository = workMaterialFirestoreRepository;
    }

    @Override
    public Optional<Course> save(Course model) {
        return courseFirestoreRepository.save(model).blockOptional();
    }

    @Override
    public Optional<Course> delete(String id) {
        var course = courseFirestoreRepository.findById(id).blockOptional();
        courseFirestoreRepository.deleteById(id);
        return course;
    }

    @Override
    public List<Course> findCoursesByStudentId(String studentId) {
        return getCourseClassroom.findCoursesByStudentId(studentId);
    }

    @Override
    public Optional<Course> findOneCourseByStudentId(String courseId, String studentId) {
        return getCourseClassroom.findOneCourseByStudentId(courseId, studentId);
    }

    @Override
    public List<Course.Announcement> getAllAnnouncementByCourse(Course course) {
        return getCourseAnnouncementClassroom.getAllAnnouncementByCourse(course);
    }

    @Override
    public List<Course.WorkMaterial> listAllWorks(Course course) {
        return getCourseWorkClassroom.listAllWorks(course);
    }

    @Override
    public List<Course.WorkMaterial> listAllWorkMaterial(Course course, String access) {
        return getCourseWorkMaterialClassroom.listAllWorkMaterial(course);
    }

    @Override
    public List<Course.WorkMaterial> listAllWorkMaterial(Course course) {
        return getCourseWorkMaterialClassroom.listAllWorkMaterial(course);
    }

    @Override
    public Optional<Course.WorkMaterial> findOneMaterial(Course course, String materialId) {
        return getCourseWorkMaterialClassroom.findOneMaterial(course, materialId);
    }

    @Override
    public Course.WorkMaterial upsetCourseMaterial(Course.WorkMaterial materialSaved) {
        return workMaterialFirestoreRepository.save(materialSaved).block();
    }
}
