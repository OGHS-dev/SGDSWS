package com.oghs.sgdsws;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.oghs.sgdsws.util.LoginSuccessMesage;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private final String QUERY_USERS_BY_USERNAME = "SELECT u.NOMBRE_USUARIO, u.CONTRASENA, u.ESTATUS FROM USUARIO u WHERE NOMBRE_USUARIO = ? AND ESTATUS = 1";
    private final String QUERY_ROLES_BY_USERNAME = "SELECT u.NOMBRE_USUARIO, r.DESCRIPCION FROM ROL r INNER JOIN USUARIO u ON r.ID_ROL = u.ID_ROL WHERE u.NOMBRE_USUARIO = ?";
    
    @Autowired
    private DataSource dataSource;

    @Autowired
    private BCryptPasswordEncoder passEncoder;

    @Autowired
    private LoginSuccessMesage loginSuccessMesage;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable().authorizeHttpRequests()
        .requestMatchers("/", "/index", "/home", "/img/**", "/webjars/**").permitAll()
        .requestMatchers("/views/proyectos/").hasAnyRole("ADMIN", "SUPERVISOR", "AUDITOR", "REVISOR", "DESARROLLO")
        .requestMatchers("/views/proyectos/crear").hasAnyRole("ADMIN", "SUPERVISOR", "AUDITOR", "REVISOR")
        .requestMatchers("/views/proyectos/guardar").hasAnyRole("ADMIN", "SUPERVISOR", "AUDITOR", "REVISOR")
        .requestMatchers("/views/proyectos/editar/**").hasAnyRole("ADMIN", "SUPERVISOR", "AUDITOR", "REVISOR")
        .requestMatchers("/views/proyectos/eliminar/**").hasAnyRole("ADMIN", "SUPERVISOR", "AUDITOR", "REVISOR")
        .requestMatchers("/views/proyectos/detalle/**").hasAnyRole("ADMIN", "SUPERVISOR", "AUDITOR", "REVISOR", "DESARROLLO")
        .anyRequest().authenticated()
        .and()
        .formLogin().successHandler(loginSuccessMesage).loginPage("/login").permitAll()
        .and()
        .logout().permitAll();

        return http.build();
    }

    @Autowired
    public void configurerSecurityGlobal(AuthenticationManagerBuilder amBuilder) throws Exception {
        amBuilder.jdbcAuthentication()
        .dataSource(dataSource)
        .passwordEncoder(passEncoder)
        .usersByUsernameQuery(QUERY_USERS_BY_USERNAME)
        .authoritiesByUsernameQuery(QUERY_ROLES_BY_USERNAME);
    }
}
