package kmd.backend.magazine.controllers;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kmd.backend.magazine.models.User;
import kmd.backend.magazine.services.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // @PostMapping("/add")
    // public ResponseEntity<?> addUser(@RequestBody User user) {
    //     return ResponseEntity.ok().body(userService.saveUser(user));
    // }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable int userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok().body("User deleted");
    }

    @PostMapping("/uploadProfile")
    public ResponseEntity<?> profilePhotoUpload(@RequestParam("user")User user, @RequestParam("profilePhoto")MultipartFile profilePhoto)throws IOException{
         userService.savedUser(user,profilePhoto);
         return ResponseEntity.ok().body("User Added!!"); 
    }

}
