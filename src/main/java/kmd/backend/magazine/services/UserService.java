package kmd.backend.magazine.services;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

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

    // public User savedUser(User user, MultipartFile profilePhoto) throws IOException {
    //     List<User> existingUsers = usersRepo.findByName(user.getName());
    //     if (existingUsers.isEmpty()) {
    //         user.setProfilePhotoName(profilePhoto.getOriginalFilename());
    //         User savedUser = usersRepo.save(user);
    //         if (profilePhoto != null) {
    //             commonService.fileLocalUpload(profilePhoto, "profilePhoto", savedUser.getId());
    //         }
    //         return savedUser;
    //     } else {
    //         throw new EntityAlreadyExistException("User Added");
    //     }
    // }

    public void deleteUserById(int userId) {

        User user = usersRepo.findById(userId).get();
        if (user == null) {
            usersRepo.deleteById(userId);
        } else {
            throw new EntityNotFoundException("Role");
        }

    }

    public User saveUser(MultipartFile file, User user) throws Exception {

        List<User> existingUsers = usersRepo.findByName(user.getName());
        if (existingUsers.isEmpty()) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            try {
                if(!file.isEmpty()) {
                    if (fileName.contains("..")) {
                        throw new Exception("Filename contains invalid path sequence "
                                + fileName);
                    }
                    System.out.println(file.getBytes());
                    user.setProfilePhotoData(file.getBytes());
                    user.setProfilePhotoName(fileName);
                    user.setProfilePhotoType(file.getContentType());
                }

                return usersRepo.save(user);
            } catch (Exception e) {
                throw new Exception("Could not save File: " + fileName);
            }
        } else {
            throw new EntityAlreadyExistException("User is already added");
        }

    }

    public User getUser(int userId) throws Exception {
        return usersRepo.findById(userId)
                .orElseThrow(
                        () -> new Exception("User not found"));
    }

    // public User uploadProfilePhoto(User user, MultipartFile profilePhoto)throws
    // IOException{
    // if (user)
    // user.setProfilePhoto(profilePhoto.getOriginalFilename());
    // commonService.fileLocalUpload(profilePhoto, "profilePhoto",user.getId());
    // }

}