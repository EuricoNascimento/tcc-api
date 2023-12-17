package com.github.edulook.look.infra.repository.course.classroom.mapper.factories;

import com.github.edulook.look.core.data.PageContent;
import com.github.edulook.look.core.data.Typename;
import com.github.edulook.look.core.model.Course;
import com.google.api.services.classroom.model.Material;
import org.springframework.security.crypto.codec.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;

public class CourseMaterialLinkFactory implements AbstractCourseMaterialFactory {
    @Override
    public Course.WorkMaterial.Material create(Material source) {
        if(source == null)
            throw new IllegalArgumentException("Material can't be null");

        return Course.WorkMaterial.Material
            .builder()
            .id(hash256(source.getLink().getUrl()))
            .name(source.getDriveFile().getDriveFile().getTitle())
            .originLink(source.getLink().getUrl())
            .previewLink(source.getLink().getThumbnailUrl())
            .type(Typename.WEB)
            .description(PageContent.withDefaults(source.getDriveFile().getDriveFile().getTitle()))
            .build();

    }
}