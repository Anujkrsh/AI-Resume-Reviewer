import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/resume-reviewer")
public class UploadResumeController {

    private static final String UPLOAD_DIR = "uploads";

    @PostMapping("/upload")
    public ResponseEntity<String> uploadResume(@RequestParam("file") MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("Invalid file: file is missing or empty.");
        }
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isBlank()) {
            return ResponseEntity
                    .badRequest()
                    .body("Invalid file: filename is missing.");
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        String fileNameInLowerCase = fileName.toLowerCase();
        if(!fileNameInLowerCase.endsWith(".pdf") && !fileNameInLowerCase.endsWith(".doc") && !fileNameInLowerCase.endsWith(".docx")) {
            return ResponseEntity.badRequest().body("Invalid file format, only pdf and word documents are supported");
        }
        try{
            Path uploadPath = Paths.get(UPLOAD_DIR);
            Path filePath = uploadPath.resolve(fileNameInLowerCase);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return ResponseEntity.ok("File uploaded successfully"+fileName);
        }
        catch(IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("File upload failed."+e.getMessage());
        }
    }




}
