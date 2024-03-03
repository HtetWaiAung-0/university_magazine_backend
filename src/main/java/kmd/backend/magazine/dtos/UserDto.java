package kmd.backend.magazine.dtos;

import kmd.backend.magazine.models.Faculty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private int id;
    private String name;
    private String role;
    private String profilePhoto; 
    private boolean deleteStatus;
    private Faculty faculty;
}
