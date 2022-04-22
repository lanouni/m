package ma.emsi.patientsmvc.repositories;

import ma.emsi.patientsmvc.entites.Pat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Pat, Long> {
    Page<Pat> findByNomContains(String kw, Pageable pageable);
}
