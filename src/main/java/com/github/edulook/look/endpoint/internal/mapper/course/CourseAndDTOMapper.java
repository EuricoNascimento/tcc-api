package com.github.edulook.look.endpoint.internal.mapper.course;

import java.util.List;

import com.github.edulook.look.core.model.*;
import com.github.edulook.look.endpoint.io.course.*;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseAndDTOMapper {
    CourseDTO toDTO(Course source);
    Course toModel(CourseDTO source);
    AnnouncementDTO toModel(Announcement source);
    List<CourseDTO> toDTOList(List<Course> source);
    MaterialDTO toDTO(WorkMaterial source);
    PageDTO toDTO(Page source);
    ContentPageDTO toDTO(PageContent source);
    ContentMaterialDTO toDTO(Material source);
    SimpleMaterialDTO toSimpleDTO(WorkMaterial source);
}
