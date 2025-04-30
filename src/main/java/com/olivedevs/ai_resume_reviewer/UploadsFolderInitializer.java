package com.olivedevs.ai_resume_reviewer;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class UploadsFolderInitializer {

    @PostConstruct
    public void initialize() throws IOException {
            Path uploadPath = Paths.get("uploads");
            if(!Files.exists(uploadPath))
                Files.createDirectories(uploadPath);

    }
}
