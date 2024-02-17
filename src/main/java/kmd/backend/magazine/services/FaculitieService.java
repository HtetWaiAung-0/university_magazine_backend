package kmd.backend.magazine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmd.backend.magazine.repos.FaculitiesRepo;

@Service
public class FaculitieService {
    @Autowired
    private FaculitiesRepo faculitiesRepo;
}
