package kmd.backend.magazine.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmd.backend.magazine.exceptions.EntityAlreadyExistException;
import kmd.backend.magazine.exceptions.EntityNotFoundException;
import kmd.backend.magazine.models.Role;
import kmd.backend.magazine.repos.RoleRepo;

@Service
public class RoleService {
    @Autowired
    private RoleRepo roleRepo;

    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }

    public Role saveRole(Role role) {
        List<Role> existingRole = roleRepo.findByRole(role.getRole());
        if(existingRole.isEmpty()) {
            return roleRepo.save(role);
            
        }else{
            throw new EntityAlreadyExistException("Role");
        }
        
    }

    public void deleteRole(int roleId) {

        Optional<Role> role = roleRepo.findById(roleId);
        if (role.isPresent()) {
            roleRepo.deleteById(roleId);
        } else {
            throw new EntityNotFoundException("Role");
        }

    }
}

