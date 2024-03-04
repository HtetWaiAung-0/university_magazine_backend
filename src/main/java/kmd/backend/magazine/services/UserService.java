package kmd.backend.magazine.services;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import kmd.backend.magazine.dtos.UserDto;
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

    public void deleteUserById(int userId) {

        User user = usersRepo.findById(userId).get();
        if (user == null) {
            usersRepo.deleteById(userId);
        } else {
            throw new EntityNotFoundException("Role");
        }

    }

    public void deleteUserPhoto(int userId){
        User user = usersRepo.findById(userId).get();
        if (user == null) {
            throw new EntityNotFoundException("User");
        }
            user.setProfilePhotoData(null);
            user.setProfilePhotoName(null);
            user.setProfilePhotoType(null);
            usersRepo.save(user);
    }

    public User updateUser(MultipartFile file, User updateUser, int userId) throws Exception {
       User user = getUserRaw(userId);
        if(user != null) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            try {
                if (!file.isEmpty()) {
                    if (fileName.contains("..")) {
                        throw new Exception("Filename contains invalid path sequence "
                                + fileName);
                    }
                    System.out.println(file.getBytes());
                    user.setProfilePhotoData(file.getBytes());
                    user.setProfilePhotoName(fileName);
                    user.setProfilePhotoType(file.getContentType());
                }
                    user.setName(updateUser.getName());
                    user.setRole(updateUser.getRole());

                user.setFaculty(updateUser.getFaculty());
                return usersRepo.save(user);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }else{
            throw new EntityNotFoundException("Update User Not Found!");
        }
    }

    public User saveUser(MultipartFile file, User user) throws Exception {
        List<User> existingUsers = usersRepo.findByName(user.getName());
        if (existingUsers.isEmpty()) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            try {
                if (!file.isEmpty()) {
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

    public UserDto getUser(int userId) {
        User user = new User();
        try {
            user = usersRepo.findById(userId).get();
        } catch (Exception e) {
            throw new EntityNotFoundException("User not found!");
        }
        String downloadURL = commonService.fileDownloadURL("api/v1/user/profilePhoto", user.getProfilePhotoData(),
                user.getProfilePhotoName(), user.getId());
        return new UserDto(user.getId(), user.getName(), user.getRole(), downloadURL, user.isDeleteStatus(),
                user.getFaculty());
    }

    public User getUserRaw(int userId) {
        return usersRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));
    }

}