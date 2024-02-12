package kmd.backend.magazine.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kmd.backend.magazine.models.Role;
import kmd.backend.magazine.services.RoleService;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping()
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @PostMapping("/add")
    public Role addRole(@RequestBody Role role){
        //System.out.println("Role: " + role.toString());
        return roleService.saveRole(role);
    }

    @DeleteMapping("/{roleId}")
    public void deleteRole(@PathVariable int roleId){
        roleService.deleteRole(roleId);
    }

}
