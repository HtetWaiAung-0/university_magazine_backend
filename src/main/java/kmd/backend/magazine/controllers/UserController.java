package kmd.backend.magazine.controllers;

import java.io.IOException;
import java.util.List;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

import kmd.backend.magazine.dtos.UserDto;
import kmd.backend.magazine.models.User;
import kmd.backend.magazine.services.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam("username") String username, @RequestParam("password") String password)
            throws AuthenticationException {
        userService.authenticateUser(username, password);
        return ResponseEntity.ok().body("Authention successful");
    }

    @PostMapping("/changepassword/{id}")
    public ResponseEntity<?> changePassword(@PathVariable int id,
            @RequestParam("password") String password, @RequestParam("newPassword") String newPassword)
            throws Exception {
        userService.changeUserPassword(password, newPassword, id);
        return ResponseEntity.ok().body("Password changed");
    }

    @GetMapping("/check/{username}")
    public ResponseEntity<?> checkUserName(@RequestParam("username") String username) {
        return ResponseEntity.ok().body(userService.checkUserName(username));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable int userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok().body("User deleted");
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestParam("profilePhoto") MultipartFile profilePhoto,
            @RequestParam("user") String userStr) throws Exception {
        User user = objectMapper.readValue(userStr, User.class);
        userService.saveUser(profilePhoto, user);
        return ResponseEntity.ok().body("User Added!");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable int userId) throws Exception {
        return ResponseEntity.ok().body(userService.getUser(userId));
    }

    @PostMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@RequestParam("profilePhoto") MultipartFile profilePhoto,
            @RequestParam("user") String userUpdateStr, @PathVariable int userId) throws Exception {
        User updateUser = objectMapper.readValue(userUpdateStr, User.class);
        userService.updateUser(profilePhoto, updateUser, userId);
        return ResponseEntity.ok().body("User Updated!");
    }

    @DeleteMapping("/delete/profilePhoto/{userId}")
    public ResponseEntity<?> deleteUserPhoto(@PathVariable int userId) throws Exception {
        userService.deleteUserPhoto(userId);
        return ResponseEntity.ok().body("User photo deleted");
    }

    @GetMapping("/profilePhoto/download/{userId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable int userId) throws Exception {
        User user = userService.getUserRaw(userId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(user.getProfilePhotoType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + user.getProfilePhotoName()
                                + "\"")
                .body(new ByteArrayResource(user.getProfilePhotoData()));
    }

}
