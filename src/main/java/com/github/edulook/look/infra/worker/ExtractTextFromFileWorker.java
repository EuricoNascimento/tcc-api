

package com.github.edulook.look.infra.worker;

import com.github.edulook.look.core.model.PageContent;
import com.github.edulook.look.core.exceptions.ResourceNotFoundException;
import com.github.edulook.look.core.exceptions.TextExtractInvalidException;
import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.model.WorkMaterial;
import com.github.edulook.look.core.repository.CourseRepository;
import com.github.edulook.look.core.repository.PageContentRepository;
import com.github.edulook.look.infra.worker.events.course.CourseMaterialExtractPDFEvent;
import com.github.edulook.look.infra.worker.exceptions.InvalidEventException;
import com.github.edulook.look.service.DriveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Configuration
@EnableAsync
public class ExtractTextFromFileWorker {
    private final DriveService driveService;
    private final CourseRepository courseRepository;
    private final PageContentRepository pageContentRepository;

    @Value("${look.application.data}")
    private String localData;

    public ExtractTextFromFileWorker(DriveService driveService, CourseRepository courseRepository, PageContentRepository pageContentRepository) {
        this.driveService = driveService;
        this.courseRepository = courseRepository;
        this.pageContentRepository = pageContentRepository;
    }


    @Async
    @EventListener
    public void workMaterialProcessor(CourseMaterialExtractPDFEvent event) {
        if(event.isNotValid()) {
            log.warn("Event invalid {}", event);
            throw new InvalidEventException(
                String.format("%s::Invalid event cannot processed", CourseMaterialExtractPDFEvent.class));
        }

        var range = event.getOption()
            .getRange();

        var material = getWorkMaterial(event.getCourseId(), event.getMaterialId())
            .orElseThrow(ResourceNotFoundException::new);

        var pdf = downloadFile(material, event.getContentId())
            .orElseThrow(TextExtractInvalidException::new);

        try {
            event.getClassification()
                .instance()
                .extract(pdf, range)
                .ifPresentOrElse(pageContent -> {
                    pageContent.setId(UUID.randomUUID());
                    pageContent.getPages().forEach(it -> {
                        it.setId(UUID.randomUUID());
                        it.setPageContent(pageContent);
                    });
                    var materialSave = upsetContent(event.getContentId(), material, pageContent);
                    courseRepository.upsetCourseMaterial(materialSave);
                }, TextExtractInvalidException::new);
        } catch (Exception e) {
          log.error("error::", e);
        } finally {
            var filename = pdf.getName();
            var deleteStatus = pdf.delete();
            log.info("file deleted: {} - {}", filename, deleteStatus);
        }
    }

    private WorkMaterial upsetContent(String contentId, WorkMaterial material, PageContent content) {
        var contentPDF = material.getMaterials().stream()
            .filter(it -> it.getId().equalsIgnoreCase(contentId))
            .findFirst()
            .orElseThrow(ResourceNotFoundException::new);

        var pageContentSaved = pageContentRepository.save(content);

        contentPDF.setContent(pageContentSaved);

        material.setMaterials(material.getMaterials().stream()
            .map(it -> contentPDF.getId().equalsIgnoreCase(it.getId()) ? contentPDF : it)
            .toList());

        return material;
    }

    private Optional<File> downloadFile(WorkMaterial material, String contentId) {
        var content = material.getMaterials().stream()
            .filter(it -> it.getId().equalsIgnoreCase(contentId))
            .findFirst();

        return content.flatMap(it -> driveService.downloadViaSharedLink(it.getOriginLink(), localData));
    }

    private Optional<WorkMaterial> getWorkMaterial(String courseId, String materialId) {
        var course = Course.builder()
            .id(courseId)
            .build();
        return courseRepository.findOneMaterial(course, materialId);
    }
}

