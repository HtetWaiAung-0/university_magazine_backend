package kmd.backend.magazine.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmd.backend.magazine.models.Role;
import kmd.backend.magazine.repos.RoleRepo;

@Service
public class RoleService {
    @Autowired
    private RoleRepo rolesRepo;

    public List<Role> getAllRoles() {
        return rolesRepo.findAll();
    }

    public Role saveRole(Role role) {
        if(role == null){
            throw new IllegalArgumentException("Role is null");
        }
        return rolesRepo.save(role);
    }

    public void deleteRole(int roleId) {
        rolesRepo.deleteById(roleId);
    }

}
