package kmd.backend.magazine.services;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kmd.backend.magazine.exceptions.EntityAlreadyExistException;
import kmd.backend.magazine.exceptions.EntityNotFoundException;
import kmd.backend.magazine.models.AdminUser;
import kmd.backend.magazine.models.Role;
import kmd.backend.magazine.repos.AdminUserRepo;
import kmd.backend.magazine.repos.RoleRepo;

@Service
public class AdminUserService {
    @Autowired
    private AdminUserRepo adminUsersRepo;

    @Autowired
    private RoleRepo roleRepo;

    public List<AdminUser> getAllAdminUser() {
        return adminUsersRepo.findAll();
    }

    public AdminUser saveAdminUser(AdminUser adminuser) {
        List<AdminUser> existingAdminUser = adminUsersRepo.findByName(adminuser.getName());
        if(existingAdminUser.isEmpty()) {
            return adminUsersRepo.save(adminuser);
            
        }else{
            throw new EntityAlreadyExistException("Role");
        }
        
    }

    public void deleteAdminUser(int roleId) {

        Optional<Role> role = roleRepo.findById(roleId);
        if (role.isPresent()) {
            adminUsersRepo.deleteById(roleId);
        } else {
            throw new EntityNotFoundException("Role");
        }

    }


}
