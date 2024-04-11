package kmd.backend.magazine.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmd.backend.magazine.dtos.FacultyReportDto;
import kmd.backend.magazine.dtos.FacultyResponseDto;
import kmd.backend.magazine.dtos.ReportDto;
import kmd.backend.magazine.dtos.UserReportDto;
import kmd.backend.magazine.dtos.UserResponseDto;
import kmd.backend.magazine.models.Token;
import kmd.backend.magazine.repos.TokenRepository;

@Service
public class ReportService {
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private TokenRepository tokenRepo;

    public List<UserReportDto> getUserReport() {
        List<UserResponseDto> users = userService.getAllUsers();
        List<UserReportDto> userReports = new ArrayList<>();
        for (UserResponseDto user : users) {
            UserReportDto userReport = new UserReportDto();
            List<Token> tokens = tokenRepo.findByUserId(user.getId());
            userReport.setUserId(user.getId());
            userReport.setUserName(user.getName());
            if (tokens.isEmpty()) {
                userReport.setTotalLoginCount(0);
            } else {
                userReport.setTotalLoginCount(tokens.size());
            }
            userReports.add(userReport);
        }
        return userReports;
    }

    public List<FacultyReportDto> getFacultyReport() {
        List<FacultyReportDto> facultyReports = new ArrayList<>();
        List<FacultyResponseDto> faculties = facultyService.getAllFaculties();

        for (FacultyResponseDto faculty : faculties) {
            FacultyReportDto facultyReportDto = new FacultyReportDto();
            facultyReportDto.setFacultyId(faculty.getId());
            facultyReportDto.setFacultyName(faculty.getName());
            facultyReportDto.setTotalUsers(userService.getUsersByFacultyId(faculty.getId()).size());
            facultyReportDto.setTotalArticles(articleService.getArticlesByFacultyId(faculty.getId()).size());

            facultyReports.add(facultyReportDto);
        }
        return facultyReports;

    }

    public ReportDto getReport() {
        ReportDto report = new ReportDto();
        report.setUserReport(getUserReport());
        report.setFacultyReport(getFacultyReport());
        report.setTotalArticlesUploaded(getFacultyReport().size());
        return report;
    }


}
