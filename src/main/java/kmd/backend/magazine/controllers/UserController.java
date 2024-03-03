package kmd.backend.magazine.controllers;

import java.io.IOException;
import java.util.List;
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

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable int userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok().body("User deleted");
    }

    // @PostMapping("/add")
    // public ResponseEntity<?> profilePhotoUpload(@RequestParam("user")String userStr, @RequestParam("profilePhoto")MultipartFile profilePhoto)throws IOException{
    //      User user = objectMapper.readValue(userStr, User.class);
    //      userService.savedUser(user,profilePhoto);
    //      return ResponseEntity.ok().body("User Added!!"); 
    // }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestParam("profilePhoto")MultipartFile profilePhoto,@RequestParam("user")String userStr) throws Exception {
        //Attachment attachment = null;
        User user = objectMapper.readValue(userStr, User.class);
        userService.saveUser(profilePhoto,user);
        // downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
        //         .path("/download/")
        //         .path(attachment.getId())
        //         .toUriString();

        return ResponseEntity.ok().body("User Added!"); 
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable int userId) throws Exception {
        User user = userService.getUser(userId);
        String downloadURl = "";
        if(user.getProfilePhotoData()!= null || user.getProfilePhotoData().length > 0 || user.getProfilePhotoName()!= null){
             downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/user/download/")
                .path(String.valueOf(user.getId()))
                .toUriString();
        }
        

        UserDto userDto = new UserDto(user.getId(),user.getName(),user.getRole(),downloadURl,user.isDeleteStatus(),user.getFaculty());
        // return  ResponseEntity.ok()
        //         .contentType(MediaType.parseMediaType(user.getProfilePhotoType()))
        //         .header(HttpHeaders.CONTENT_DISPOSITION,
        //                 "attachment; filename=\"" + user.getProfilePhotoName()
        //         + "\"")
        //         .body(new ByteArrayResource(user.getProfilePhotoData()));


        return ResponseEntity.ok().body(userDto);
    }


    @GetMapping("/download/{userId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable int userId) throws Exception {
        User user = userService.getUser(userId);
        return  ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(user.getProfilePhotoType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + user.getProfilePhotoName()
                + "\"")
                .body(new ByteArrayResource(user.getProfilePhotoData()));
    }

}
