package kmd.backend.magazine.controllers;

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
import kmd.backend.magazine.models.Role;
import kmd.backend.magazine.services.RoleService;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping()
    public ResponseEntity<Object> getAllRoles() {
        return ResponseEntity.ok().body(roleService.getAllRoles());
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addRole(@RequestBody Role role){
        try{
            roleService.saveRole(role);
            return ResponseEntity.ok().body("Role added.");
        }
        catch(EntityAlreadyExistException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }/* catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } */
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<String> deleteRole(@PathVariable int roleId){
        try{
            roleService.deleteRole(roleId);
            return ResponseEntity.ok().body("Role deleted");
        }catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
