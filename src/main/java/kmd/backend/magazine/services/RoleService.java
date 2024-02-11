package kmd.backend.magazine.services;

import org.springframework.beans.factory.annotation.Autowired;

import kmd.backend.magazine.repos.RoleRepo;

public class RoleService {
    @Autowired
    private RoleRepo rolesRepo;
}
