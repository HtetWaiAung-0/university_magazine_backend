package kmd.backend.magazine.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@Service
public class CommonService {

    public void fileLocalUpload(MultipartFile file, String fileType, int id) throws IOException {
        String uploadDir = "assets/" + fileType + "/" + id + "/";
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        InputStream inputStream = file.getInputStream();
        System.out.println(inputStream);
        if (!file.isEmpty()) {
            Path filePath = uploadPath.resolve(file.getOriginalFilename());
            System.out.println(filePath.toFile().getAbsolutePath());
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    

    public String fileDownloadURL(String baseMapping,byte[] fileData,String fileName,int id) {
        if (fileData != null && fileData.length > 0
                && fileName != null) {
                    String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(baseMapping+"/download/")
                    .path(String.valueOf(id))
                    .toUriString();
                    return downloadURL;
        }
        return "";
        
    }
}
