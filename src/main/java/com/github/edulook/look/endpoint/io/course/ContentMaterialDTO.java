package com.github.edulook.look.endpoint.io.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.edulook.look.core.data.Option;
import com.github.edulook.look.core.model.PageContent;
import lombok.Builder;

import java.util.Optional;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ContentMaterialDTO(
        String id,
        String name,
        String description,
        String type,
        String origin,
        String preview,
        Optional<ContentPageDTO> content,
        Optional<Option> option
) {
}
