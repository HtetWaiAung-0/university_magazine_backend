package kmd.backend.magazine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmd.backend.magazine.repos.UsersRepo;

@Service
public class UsersService {
    @Autowired
    private UsersRepo usersRepo;
}
