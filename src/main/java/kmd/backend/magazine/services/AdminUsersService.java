package kmd.backend.magazine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmd.backend.magazine.repos.AdminUsersRepo;

@Service
public class AdminUsersService {
    @Autowired
    private AdminUsersRepo adminUsersRepo;
}
