package kmd.backend.magazine.services;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kmd.backend.magazine.exceptions.EntityAlreadyExistException;
import kmd.backend.magazine.exceptions.EntityNotFoundException;
import kmd.backend.magazine.models.User;
import kmd.backend.magazine.repos.UserRepo;

@Service
public class UserService {
    @Autowired
    private UserRepo usersRepo;

    @Autowired
    CommonService commonService;

    public List<User> getAllUsers() {
        return usersRepo.findAll();
    }

    public User getUserById(int userId) {
        return usersRepo.findById(userId).get();
    }

    public User savedUser(User user, MultipartFile profilePhoto) throws IOException {
        List<User> existingUsers = usersRepo.findByName(user.getName());
        if (existingUsers.isEmpty()) {
            user.setProfilePhoto(profilePhoto.getOriginalFilename());
            User savedUser = usersRepo.save(user);
            if (profilePhoto != null) {
                commonService.fileLocalUpload(profilePhoto, "profilePhoto", savedUser.getId());
            }
            return savedUser;
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

    // public User uploadProfilePhoto(User user, MultipartFile profilePhoto)throws
    // IOException{
    // if (user)
    // user.setProfilePhoto(profilePhoto.getOriginalFilename());
    // commonService.fileLocalUpload(profilePhoto, "profilePhoto",user.getId());
    // }

}