package com.github.edulook.look.service;

import com.github.edulook.look.core.exceptions.ResourceNotFoundException;
import com.github.edulook.look.core.exceptions.TextExtractInvalidException;
import com.google.api.services.drive.Drive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Service
public class DriveService {
    private final Drive drive;

    public DriveService(Drive drive) {
        this.drive = drive;
    }

    public Optional<File> download(String fileId) {
        return download(fileId, "./");
    }

    public Optional<File> download(String fileId, String pathToSave) {
        if(fileId.isBlank())
            throw new TextExtractInvalidException("file id is invalid");

        try(var outputStream = new ByteArrayOutputStream()) {
            var originFile = Optional.ofNullable(drive.files().get(fileId).execute())
                .orElseThrow(ResourceNotFoundException::new);

            var pathResolved = Paths.get(pathToSave + "/" + originFile.getName())
                .normalize()
                .toAbsolutePath();

            Path path = Paths.get(pathToSave).toAbsolutePath();
            if (!Files.exists(path)){
                Files.createDirectories(path);
            }

            var saveTo = pathResolved.toFile();

            drive.files()
                .get(originFile.getId())
                .executeMediaAndDownloadTo(outputStream);

            try(var outputLocalFile = new FileOutputStream(saveTo)) {
                outputLocalFile.write(outputStream.toByteArray());
            }

            return Optional.of(saveTo);

        } catch (IOException | ResourceNotFoundException e) {
            log.warn("error::", e);
            return Optional.empty();
        }
    }

    public Optional<File> downloadViaSharedLink(String sharedLink) {
        return downloadViaSharedLink(sharedLink, "./");
    }

    public Optional<File> downloadViaSharedLink(String sharedLink, String pathToSave) {
        var fileId = getIdFromDriveFile(sharedLink);

        if(fileId.isBlank()) {
            log.error("file id is invalid");
            return Optional.empty();
        }

        return download(fileId, pathToSave);
    }

    private String getIdFromDriveFile(String url) {
        var regex = "\\/d\\/(?<id>[\\w+_-]+)\\/";

        var pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        var matcher = pattern.matcher(url);

        if(matcher.find())
            return matcher.group("id");

        return "";
    }
}

