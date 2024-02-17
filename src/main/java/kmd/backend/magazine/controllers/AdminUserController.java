package kmd.backend.magazine.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import kmd.backend.magazine.exceptions.EntityAlreadyExistException;
import kmd.backend.magazine.exceptions.EntityNotFoundException;
import kmd.backend.magazine.models.AdminUser;
import kmd.backend.magazine.services.AdminUserService;


@RestController
@RequestMapping("/api/v1/adminuser")
public class AdminUserController {

    @Autowired
    private AdminUserService adminuserService;


    @GetMapping()
    public List<AdminUser> getAllAdminUser() {
        return adminuserService.getAllAdminUser();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addAdminUser(@RequestBody AdminUser adminuser) {
        try {
            adminuserService.saveAdminUser(adminuser);
            return ResponseEntity.ok().body("Admin added");
        } catch (EntityAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<String> deleteAdminUserByRole(@PathVariable int roleId){
        try{
          adminuserService.deleteAdminUser(roleId);
            return ResponseEntity.ok().body("AdminUser deleted");
        }catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}