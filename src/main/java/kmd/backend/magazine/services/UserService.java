package kmd.backend.magazine.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmd.backend.magazine.exceptions.EntityAlreadyExistException;
import kmd.backend.magazine.exceptions.EntityNotFoundException;

import kmd.backend.magazine.models.User;
import kmd.backend.magazine.repos.UserRepo;

@Service
public class UserService {
    @Autowired
    private UserRepo usersRepo;

    public List<User> getAllUsers() {
        return usersRepo.findAll();
    }

    public User getUserById(int userId) {
        return usersRepo.findById(userId).get();
    }

    public User saveUser(User user) {
        List<User> existingUsers = usersRepo.findByName(user.getName());
        if (existingUsers.isEmpty()) {
            return usersRepo.save(user);

        } else {
            throw new EntityAlreadyExistException("User");
        }
    }

    public void deleteUserById(int userId) {

        User user = usersRepo.findById(userId).get();
        if (user==null) {
            usersRepo.deleteById(userId);
        } else {
            throw new EntityNotFoundException("Role");
        }

    }

}