package kmd.backend.magazine.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportDto {

    int totalArticlesUploaded;
    List<UserReportDto> userReport;
    List<FacultyReportDto> facultyReport;
    
}
