package ma.emsi.patientsmvc.sec.Service;

import ma.emsi.patientsmvc.sec.entities.AppRole;
import ma.emsi.patientsmvc.sec.entities.AppUser;

public interface SecuityService  {
    AppUser saveNewUser(String username,String password, String rePassword);
    AppRole saveNewRole(String roleName, String description);
    void addRoleToUser(String username , String roleName);
    void removeRolefromUser(String username , String roleName);
    AppUser loadByUsername(String username);
}
