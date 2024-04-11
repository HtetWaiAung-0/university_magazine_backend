package kmd.backend.magazine.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.mail.MessagingException;
import kmd.backend.magazine.dtos.UserRequestDto;
import kmd.backend.magazine.dtos.UserResponseDto;
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

    @Autowired
    EmailService emailService;
    @Autowired
    private FacultyService facultyService;

    public List<UserResponseDto> getAllUsers() {
        List<UserResponseDto> userDtos = new ArrayList<>();
        for (User user : usersRepo.findByDeleteStatus(false)) {
            String downloadURL = commonService.fileDownloadURL("api/v1/user/profilePhoto", user.getProfilePhotoData(),
                    user.getProfilePhotoName(), user.getId());
            userDtos.add(new UserResponseDto(user.getId(), user.getName(), user.getRoleAsString(), user.getEmail(),
                    downloadURL,
                    user.isDeleteStatus(),
                    user.getFaculty()));
        }
        return userDtos;
    }

    public List<User> getUsersByFacultyId(int facultyId) {
        return usersRepo.findUsersByFacultyId(facultyId);
    }
    public UserResponseDto getUser(int userId) {
        User user = new User();
        try {
            user = getUserRaw(userId);
        } catch (Exception e) {
            throw new EntityNotFoundException("User");
        }
        String downloadURL = commonService.fileDownloadURL("api/v1/user/profilePhoto", user.getProfilePhotoData(),
                user.getProfilePhotoName(), user.getId());
        return new UserResponseDto(user.getId(), user.getName(), user.getRole().toString(), user.getEmail(),
                downloadURL,
                user.isDeleteStatus(),
                user.getFaculty());
    }

    public void deleteUserById(int userId) {

        User user = getUserRaw(userId);
        if (user != null) {
            user.setDeleteStatus(true);
            usersRepo.save(user);
        } else {
            throw new EntityNotFoundException("User");
        }
    }

    public void deleteUserPhoto(int userId) {
        User user = getUserRaw(userId);
        if (user == null) {
            throw new EntityNotFoundException("User");
        }
        user.setProfilePhotoData(null);
        user.setProfilePhotoName(null);
        user.setProfilePhotoType(null);
        usersRepo.save(user);
    }

    public User updateUser(UserRequestDto userRequestDto, int userId) throws Exception {
        User user = getUserRaw(userId);
        if (user != null) {

            try {
                if (userRequestDto.getProfilePhoto() != null && !userRequestDto.getProfilePhoto().isEmpty()) {
                    String fileName = StringUtils.cleanPath(userRequestDto.getProfilePhoto().getOriginalFilename());
                    if (fileName.contains("..")) {
                        throw new Exception("Filename contains invalid path sequence "
                                + fileName);
                    }
                    user.setProfilePhotoData(userRequestDto.getProfilePhoto().getBytes());
                    user.setProfilePhotoName(fileName);
                    user.setProfilePhotoType(userRequestDto.getProfilePhoto().getContentType());
                }
                user.setName(userRequestDto.getName());
                user.setRole(User.Role.valueOf(userRequestDto.getRole()));
                if (userRequestDto.getFaculty() != 0) {
                    user.setFaculty(facultyService.getFacultyById(userRequestDto.getFaculty()));
                } else {
                    user.setFaculty(null);
                }
                return usersRepo.save(user);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid role provided.");
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } else {
            throw new EntityNotFoundException("Update User Not Found!");
        }
    }

    public User saveUser(UserRequestDto userRequestDto) throws Exception {
        User user = new User();
        Optional<User> existingUsers = usersRepo.findByNameAndDeleteStatus(userRequestDto.getName(), false);
        if (existingUsers.isEmpty()) {

            try {
                if (userRequestDto.getProfilePhoto() != null && !userRequestDto.getProfilePhoto().isEmpty()) {
                    String fileName = StringUtils.cleanPath(userRequestDto.getProfilePhoto().getOriginalFilename());
                    if (fileName.contains("..")) {
                        throw new Exception("Filename contains invalid path sequence "
                                + fileName);
                    }

                    user.setProfilePhotoData(userRequestDto.getProfilePhoto().getBytes());
                    user.setProfilePhotoName(fileName);
                    user.setProfilePhotoType(userRequestDto.getProfilePhoto().getContentType());
                }
                user.setEmail(userRequestDto.getEmail());
                user.setName(userRequestDto.getName());
                user.setRole(User.Role.valueOf(userRequestDto.getRole()));
                if (userRequestDto.getFaculty() != 0) {
                    user.setFaculty(facultyService.getFacultyById(userRequestDto.getFaculty()));
                }

                BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
                user.setPassword(bcrypt.encode(userRequestDto.getPassword()));
                return usersRepo.save(user);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid role provided.");
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } else {
            throw new EntityAlreadyExistException("User is already added");
        }
    }

    public String checkUserName(String name) {
        if (usersRepo.findByNameAndDeleteStatus(name, false).isPresent()
                || usersRepo.findByNameAndDeleteStatus(name, true).isPresent()) {
            throw new EntityAlreadyExistException("User is already added");
        } else {
            return "Username is available";
        }

    }

    public User getUserRaw(int userId) {
        User user = usersRepo.findByIdAndDeleteStatus(userId, false);
        if (user != null) {
            return user;
        } else {
            throw new EntityNotFoundException("User");
        }
    }

    public String changeUserPassword(String oldPassword, String newPassword, int userId) throws Exception {
        User user = getUserRaw(userId);
        if (user != null) {
            BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
            if (oldPassword.equals(newPassword)) {
                throw new Exception("Old Password and New Password cannot be same");
            }
            if (bcrypt.matches(oldPassword, user.getPassword())) {
                user.setPassword(bcrypt.encode(newPassword));
                usersRepo.save(user);
                return "Password Changed";
            } else {
                throw new AuthenticationException("Old Password is not correct");
            }
        } else {
            throw new EntityNotFoundException("User Not Found");
        }
    }

    public void forgetPassword(String email) throws MessagingException {
        User user = usersRepo.findByEmailAndDeleteStatus(email, false);
        if (user != null) {
            String newPassword = commonService.generateRandomWord(10);
            user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
            usersRepo.save(user);

            emailService.sendEmail(email, "User Password Reset for Magazine App",
                    "Your password have been restart. \n Please change the password in 24 hours. \n Your new password is: "
                            + newPassword);
        } else {
            throw new EntityNotFoundException("User");
        }
    }

    public void deleteUser(int userId) {
        User user = getUserRaw(userId);
        usersRepo.delete(user);
    }

}