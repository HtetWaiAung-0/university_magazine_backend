package kmd.backend.magazine.dtos;

import kmd.backend.magazine.models.AcademicYear;
import kmd.backend.magazine.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    private int id;
    private String title;
    private String file; 
    private String coverPhoto; 
    private boolean approveStatus;
    private boolean deleteStatus;
    private String createdDate;
    private String updatedDate;
    private AcademicYear academicYear;
    private User user;
}
