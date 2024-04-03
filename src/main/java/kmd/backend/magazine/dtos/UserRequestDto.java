package kmd.backend.magazine.dtos;


import org.springframework.web.multipart.MultipartFile;

import io.micrometer.common.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    private String name;
    private String password;
    private String role;
    private String email;
    private MultipartFile profilePhoto; 
    private boolean deleteStatus;
    @Nullable
    private int faculty;
}