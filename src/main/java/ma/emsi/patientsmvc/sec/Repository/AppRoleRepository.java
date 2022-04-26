package ma.emsi.patientsmvc.sec.Repository;

import ma.emsi.patientsmvc.sec.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,String> {
    AppRole findByRoleName(String roleName);
}
