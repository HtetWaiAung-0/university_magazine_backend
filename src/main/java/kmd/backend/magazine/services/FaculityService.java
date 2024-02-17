package kmd.backend.magazine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmd.backend.magazine.repos.FaculityRepo;

@Service
public class FaculityService {
    @Autowired
    private FaculityRepo faculityRepo;
}
