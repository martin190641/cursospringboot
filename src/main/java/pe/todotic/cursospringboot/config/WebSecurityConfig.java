package pe.todotic.cursospringboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pe.todotic.cursospringboot.security.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // habilitación del inicio de sesión
                .formLogin()
                .loginPage("/login")
                .permitAll()

                .and()

                // definir los permisos a rutas específicas

                .authorizeRequests()
                .antMatchers("/admin/**") // -->  /admin/cursos, /admin/usuarios, /admin/cursos/1/editar, /admin/cursos/nuevo
                .hasAnyRole("ADMIN")
                .antMatchers("/cursos/*", "/usuario/**") // --> /cursos/4, /usuario/inscribir
                .authenticated()

                .anyRequest()
                .permitAll()

                .and()

                .rememberMe().key("rememberMeKey").tokenValiditySeconds(3600) // 1 hora
                .userDetailsService(userDetailsServiceImpl)

                .and()

                .exceptionHandling().accessDeniedPage("/403")

                .and()

                .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")).logoutSuccessUrl("/"));
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
