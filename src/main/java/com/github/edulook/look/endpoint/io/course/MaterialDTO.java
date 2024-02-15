package com.github.edulook.look.endpoint.io.course;

import lombok.Builder;

import java.util.List;

@Builder
public record MaterialDTO(
    String description,
    List<ContentMaterialDTO> materials
){
}
