package kmd.backend.magazine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmd.backend.magazine.repos.Faculities;

@Service
public class FaculitiesService {
    @Autowired
    private Faculities faculitiesRepo;
}
