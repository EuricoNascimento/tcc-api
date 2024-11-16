package com.github.edulook.look.endpoint.io.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.edulook.look.core.data.Option;
import com.github.edulook.look.core.model.PageContent;
import jakarta.persistence.Column;
import lombok.Builder;

import java.util.Optional;
import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record PageDTO(
        Integer page,
        String content
) {
}
