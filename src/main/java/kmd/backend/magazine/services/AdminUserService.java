package kmd.backend.magazine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmd.backend.magazine.repos.AdminUserRepo;

@Service
public class AdminUserService {
    @Autowired
    private AdminUserRepo adminUsersRepo;
}
