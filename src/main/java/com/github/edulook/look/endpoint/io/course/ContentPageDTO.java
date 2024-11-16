package com.github.edulook.look.endpoint.io.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ContentPageDTO(
        Integer size,
        List<PageDTO> pages
) {
}
