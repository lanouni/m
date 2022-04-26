package ma.emsi.patientsmvc.sec.Service;

import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import ma.emsi.patientsmvc.sec.Repository.AppRoleRepository;
import ma.emsi.patientsmvc.sec.Repository.AppUserRepository;
import ma.emsi.patientsmvc.sec.entities.AppRole;
import ma.emsi.patientsmvc.sec.entities.AppUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class SecuityServiceImpl implements SecuityService {
    private AppRoleRepository appRoleRepository;
    private AppUserRepository appUserRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser saveNewUser(String username, String password, String rePassword) {
        if (!password.equals(rePassword)) throw new RuntimeException("mot de passe incorrect");
        String hachedPassword = passwordEncoder.encode(password);
        AppUser appUser = new AppUser();
        appUser.setUserId(UUID.randomUUID().toString());
        appUser.setUsername(username);
        appUser.setPassword(hachedPassword);
        appUser.setActive(true);
        AppUser savedAppUser=appUserRepository.save(appUser);
        return savedAppUser;
    }

    @Override
    public AppRole saveNewRole(String roleName, String description) {
        AppRole appRole= appRoleRepository.findByRoleName(roleName);
        if (appRole!=null)throw  new RuntimeException("Role "+ roleName+"already exists");
        appRole = new AppRole();
        appRole.setRoleName(roleName);
        appRole.setDescription(description);
        appRoleRepository.save(appRole);
        return appRole;
    }


    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser==null)throw  new RuntimeException("user "+ username+"not found");
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        if (appRole==null)throw  new RuntimeException("Role "+ roleName+"not found");
        appUser.getAppRoles().add(appRole);
    }

    @Override
    public void removeRolefromUser(String username, String roleName) {
        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser==null)throw  new RuntimeException("user "+ username+"not found");
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        if (appRole==null)throw  new RuntimeException("Role "+ roleName+"not found");
        appUser.getAppRoles().remove(appRole);
    }

    @Override
    public AppUser loadByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
}