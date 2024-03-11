package kmd.backend.magazine.dtos;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticelRequestDto {
    private MultipartFile file;
    private MultipartFile coverPhoto;
    private String title;
    private int academicYear;
    private int user;
}
