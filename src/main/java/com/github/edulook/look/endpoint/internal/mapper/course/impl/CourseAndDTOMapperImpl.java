package com.github.edulook.look.endpoint.internal.mapper.course.impl;

import com.github.edulook.look.core.model.*;
import com.github.edulook.look.endpoint.internal.mapper.course.CourseAndDTOMapper;
import com.github.edulook.look.endpoint.io.course.*;
import com.github.edulook.look.endpoint.io.teacher.TeacherDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class CourseAndDTOMapperImpl implements CourseAndDTOMapper {

    @Override
    public CourseDTO toDTO(Course course) {
        return CourseDTO
            .builder()
            .id(course.getId())
            .description(course.getDescription())
            .name( course.getName())
            .owner(course.getOwnerId())
            .room(course.getRoom())
            .state(course.getState())
            .teachers(toTeacherDTOList(course.getTeachers()))
            .build();
    }

    private TeacherDTO toDTO(Teacher source) {
        return TeacherDTO
            .builder()
            .id(source.getId())
            .name(source.getName())
            .email(source.getEmail())
            .photo(source.getPhoto())
            .build();
    }

    private List<TeacherDTO> toTeacherDTOList(List<Teacher> source) {
        return source
            .stream()
            .map(this::toDTO)
            .toList();
    }

    @Override
    public AnnouncementDTO toModel(Announcement source) {
        return AnnouncementDTO
            .builder()
            .id(source.getId())
            .content(source.getContent())
            .createdAt(source.getCreatedAt())
            .owner(source.getOwner())
            .build();
    }

    @Override
    public Course toModel(CourseDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toModel'");
    }

    @Override
    public List<CourseDTO> toDTOList(List<Course> source) {
        return Optional
            .ofNullable(source)
            .orElse(List.of())
            .stream()
            .map(this::toDTO)
            .toList();
    }

    @Override
    public MaterialDTO toDTO(WorkMaterial source) {
        var materials = source
            .getMaterials()
            .parallelStream()
            .map(this::toDTO)
            .toList();

        return MaterialDTO.builder()
            .description(source.getDescription())
            .createdAt(source.getCreatedAt())
            .materials(materials)
            .build();
    }

    @Override
    public PageDTO toDTO(Page source) {
        return PageDTO.builder()
                .content(source.getContent())
                .page(source.getPage())
                .build();
    }

    @Override
    public ContentPageDTO toDTO(PageContent source) {
        var pages = source.getPages()
                .stream()
                .map(this::toDTO)
                .toList();

        return ContentPageDTO.builder()
                .size(source.getSize())
                .pages(pages)
                .build();
    }

    @Override
    public ContentMaterialDTO toDTO(Material source) {
        var pageContent = source.getContent().isEmpty() ? null : this.toDTO(source.getContent().get());

        return ContentMaterialDTO.builder()
            .description(source.getDescription())
            .id(source.getId())
            .name(source.getName())
            .origin(source.getOriginLink())
            .preview(source.getPreviewLink())
            .option(source.getOption())
            .type(source.getType())
            .content(Optional.ofNullable(pageContent))
            .build();
    }

    @Override
    public SimpleMaterialDTO toSimpleDTO(WorkMaterial source) {
        return SimpleMaterialDTO.builder()
            .title(source.getTitle())
            .description(source.getDescription())
            .id(source.getId())
            .courseId(source.getCourseId())
            .createdAt(source.getCreatedAt())
            .build();
    }
}
