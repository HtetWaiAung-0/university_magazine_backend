package kmd.backend.magazine.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacultyReportDto {
    int facultyId;
    String facultyName;
    int totalUsers;
    int totalArticles;
}
