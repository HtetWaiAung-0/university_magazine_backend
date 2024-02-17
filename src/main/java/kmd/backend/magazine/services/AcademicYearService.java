package kmd.backend.magazine.services;

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
    AcademicYearRepo academicYearRepo;

    public List<AcademicYear> getAllAcademicYears() {
        return academicYearRepo.findAll();
    }

    public AcademicYear getAcademicYear(int academicYearId) {

        return academicYearRepo.findById(academicYearId)
                .orElseThrow(() -> new EntityNotFoundException("Academic Year"));
    }

    public AcademicYear addAcademicYear(AcademicYear academicYear) {
        List<AcademicYear> existingAcademicYear = academicYearRepo.findByName(academicYear.getName());

        if (existingAcademicYear.isEmpty()) {
            return academicYearRepo.save(academicYear);
        } else {
            throw new EntityAlreadyExistException(academicYear.getName()+" Academic Year");
        }
    }
}

