package kmd.backend.magazine.dtos;

import org.springframework.web.multipart.MultipartFile;

import io.micrometer.common.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticelRequestDto {
    @Nullable
    private MultipartFile file;
    @Nullable
    private MultipartFile coverPhoto;
    private String title;
    private int academicYear;
    private int user;
}
