package kmd.backend.magazine.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmd.backend.magazine.dtos.UserDto;
import kmd.backend.magazine.exceptions.EntityAlreadyExistException;
import kmd.backend.magazine.exceptions.EntityNotFoundException;
import kmd.backend.magazine.models.Faculty;
import kmd.backend.magazine.models.Role;
import kmd.backend.magazine.models.User;
import kmd.backend.magazine.repos.FaculityRepo;
import kmd.backend.magazine.repos.RoleRepo;
import kmd.backend.magazine.repos.UserRepo;

@Service
public class UserService {
    @Autowired
    private UserRepo usersRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private FaculityRepo faculityRepo;

    public List<User> getAllUsers() {
        return usersRepo.findAll();
    }

    public User getUserById(int userId) {
        return usersRepo.findById(userId).get();
    }

    public User saveUser(UserDto userDto) {
        Role role = roleRepo.findById(userDto.getRoleId()).get();
        Faculty faculty = faculityRepo.findById(userDto.getFacultyId()).get();
        User user = new User();
        user.setName(userDto.getName());
        user.setRoles(role);
        user.setFaculties(faculty);
         List<User> existingUsers = usersRepo.findByName(userDto.getName());
         if (existingUsers.isEmpty()) {
             return usersRepo.save(user);

         } else {
             throw new EntityAlreadyExistException("User Added");
         }
    }

    public void deleteUserById(int userId) {

        User user = usersRepo.findById(userId).get();
        if (user == null) {
            usersRepo.deleteById(userId);
        } else {
            throw new EntityNotFoundException("Role");
        }

    }

}