package ma.emsi.patientsmvc.sec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;


@Configuration  // dire que c'est une classe de configuration  va etre instancier au premier
@EnableWebSecurity // executer la securite web
public class SecurityConfg extends WebSecurityConfigurerAdapter  {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserDetailsService userDetailsService;

    // comment sprint security va chercher les user et les roles utiliser une base de donnee ou en memoire
    // en memoire
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // utiliser passwordencoder pour coder le mot de passe

//
//        String encodePWD = passwordEncoder.encode("1234");
//        System.out.println(encodePWD);


        // il faut identifier l'algorithme avec lequel on va coder le mot de passe
        // {noop} ne pas coder le mot de passe
       /* auth.inMemoryAuthentication()
                .withUser("user1").password(encodePWD).roles("USER")
                .and()
                .withUser("user2").password(encodePWD).roles("USER")
                .and()
                .withUser("admiin").password(encodePWD).roles("USER","ADMIN");*/

       /* auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username as principal, password as credentials,active from users where username=?")
                .authoritiesByUsernameQuery("select username as principal, role as role from users_roles where username=?")
                .rolePrefix("ROLE_")
                .passwordEncoder(passwordEncoder);*/

        auth.userDetailsService(userDetailsService);

    }

    // specifier les droit d'accees

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // utilsier le loggin par defaut
        http.formLogin();
        http.authorizeRequests().antMatchers("/").permitAll();
        //http.authorizeRequests().antMatchers("/delete/**","/edit/**","/save/**","formLoginPatient/**").hasRole("ADMIN");

//        http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
//        http.authorizeRequests().antMatchers("/user/**").hasRole("USER");
        http.authorizeRequests().antMatchers("/admin/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/user/**").hasAuthority("USER");

        // authoriser l'utilisateur a acceder au fichier static sans login
        http.authorizeRequests().antMatchers("/webjars/**").permitAll();

        // toutes les requettes HTTP ont besoin d'une identification
        http.authorizeRequests().anyRequest().authenticated();
        //http.authorizeRequests()

        http.exceptionHandling().accessDeniedPage("/403");
    }


}