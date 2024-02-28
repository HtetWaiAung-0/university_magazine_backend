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
import com.fasterxml.jackson.databind.ObjectMapper;

import kmd.backend.magazine.models.Article;
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

    // @PostMapping("/add")
    // public ResponseEntity<?> addUser(@RequestBody User user) {
    //     return ResponseEntity.ok().body(userService.saveUser(user));
    // }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable int userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok().body("User deleted");
    }

    @PostMapping("/add")
    public ResponseEntity<?> profilePhotoUpload(@RequestParam("user")String userStr, @RequestParam("profilePhoto")MultipartFile profilePhoto)throws IOException{
         User user = objectMapper.readValue(userStr, User.class);
         userService.savedUser(user,profilePhoto);
         return ResponseEntity.ok().body("User Added!!"); 
    }

}
