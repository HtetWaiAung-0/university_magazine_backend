package kmd.backend.magazine.services;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmd.backend.magazine.exceptions.EntityAlreadyExistException;
import kmd.backend.magazine.exceptions.EntityNotFoundException;
import kmd.backend.magazine.models.AcademicYear;
import kmd.backend.magazine.repos.AcademicYearRepo;
@Service
public class AcademicYearService {
    @Autowired
    private AcademicYearRepo academicYearRepo;

    public List<AcademicYear> getAllAcademicYears() {
        return academicYearRepo.findAll();
    }

    public AcademicYear getAcademicYear(int academicYearId) {
        return academicYearRepo.findById(academicYearId)
                .orElseThrow(() -> new EntityNotFoundException("AcademicYear ID : " + academicYearId));
    }

    public AcademicYear saveAcademicYear(AcademicYear academicYear) {
        List<AcademicYear> existingAcademicYears = academicYearRepo.findByName(academicYear.getName());
        if (existingAcademicYears.isEmpty()) {
            return academicYearRepo.save(academicYear);
        } else {
            throw new EntityAlreadyExistException("AcademicYear Name : " + academicYear.getName());
        }

    }

    public void deleteAcademicYear(int academicYearId) {
        AcademicYear academicYear = academicYearRepo.findById(academicYearId).get();
        if (academicYear != null) {
            academicYearRepo.deleteById(academicYearId);
        } else {
            throw new EntityNotFoundException("AcademicYear ID : " + academicYearId);
        }
    }

    public AcademicYear addAcademicYear(AcademicYear academicYear) {
        List<AcademicYear> existingAcademicYear = academicYearRepo.findByName(academicYear.getName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate startDate = LocalDate.parse(academicYear.getStartDate(), formatter);
        LocalDate lastSubmitDate = LocalDate.parse(academicYear.getLastSubmitDate(), formatter);
        LocalDate endDate = LocalDate.parse(academicYear.getEndDate(), formatter);

        if (startDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Start Date cannot be before today");
        }
        if (lastSubmitDate.isBefore(startDate.plusWeeks(2))){
            throw new IllegalArgumentException("Last Submit Date must be at least 2 weeks after Start Date");
        }
        if(endDate.isBefore(lastSubmitDate.plusWeeks(2))){
            throw new IllegalArgumentException("End Date must be at least 2 weeks after Last Submit Date");
        }

        if (existingAcademicYear.isEmpty()) {
            return academicYearRepo.save(academicYear);
        } else {
            throw new EntityAlreadyExistException(academicYear.getName() + " Academic Year");
        }
    }
}


