package ma.emsi.patientsmvc;

import ma.emsi.patientsmvc.entites.Pat;
import ma.emsi.patientsmvc.repositories.PatientRepository;
import ma.emsi.patientsmvc.sec.Service.SecuityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class PatientsMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientsMvcApplication.class, args);
    }

    //@Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository){
        return args -> {
          patientRepository.save(new Pat(null,"souhayl",new Date(),false,120));
          patientRepository.save(new Pat(null,"achraf",new Date(),true,120));
          patientRepository.save(new Pat(null,"majda",new Date(),true,120));
          patientRepository.save(new Pat(null,"aya",new Date(),false,120));
          patientRepository.save(new Pat(null,"khawla",new Date(),false,120));

          patientRepository.findAll().forEach(p->{
              System.out.println(p.getNom());
          });

        };
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    //@Bean
    CommandLineRunner saveUser(SecuityService secuityService){
        return args -> {
            secuityService.saveNewUser("souhayl","1234","1234");
            secuityService.saveNewUser("test","1234","1234");
            secuityService.saveNewUser("achraf","1234","1234");
            secuityService.saveNewRole("USER","");
            secuityService.saveNewRole("ADMIN","");

            secuityService.addRoleToUser("souhayl","ADMIN");
            secuityService.addRoleToUser("souhayl","USER");
            secuityService.addRoleToUser("test","USER");
            secuityService.addRoleToUser("achraf","USER");

        };
    }
}
