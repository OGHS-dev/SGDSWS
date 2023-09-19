package com.oghs.sgdsws.config;

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

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
// @EnableWebMvc
public class WebSecurityConfig {

    private final String QUERY_USERS_BY_USERNAME = "SELECT u.NOMBRE_USUARIO, u.CONTRASENA, u.ESTATUS FROM TBL_USUARIO u WHERE NOMBRE_USUARIO = ? AND ESTATUS = 1";
    private final String QUERY_ROLES_BY_USERNAME = "SELECT u.NOMBRE_USUARIO, r.DESCRIPCION FROM TBL_ROL r INNER JOIN TBL_USUARIO u ON r.ID_ROL = u.ID_ROL WHERE u.NOMBRE_USUARIO = ?";
    
    @Autowired
    private DataSource dataSource;

    @Autowired
    private BCryptPasswordEncoder passEncoder;

    @Autowired
    private LoginSuccessMesage loginSuccessMesage;

    // @Override
    // public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //     registry
    //         .addResourceHandler("/webjars/**")
    //         .addResourceLocations("/webjars/");
    // }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable())
            .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                .requestMatchers("/login", "/", "/home", "/index", "/img/**", "/webjars/**").permitAll()
                .requestMatchers("/views/proyectos/").hasAnyRole("ADMIN", "SUPERVISOR", "AUDITOR", "REVISOR", "DESARROLLO")
                .requestMatchers("/views/proyectos/crear").hasAnyRole("ADMIN", "SUPERVISOR", "AUDITOR", "REVISOR")
                .requestMatchers("/views/proyectos/guardar").hasAnyRole("ADMIN", "SUPERVISOR", "AUDITOR", "REVISOR")
                .requestMatchers("/views/proyectos/editar/**").hasAnyRole("ADMIN", "SUPERVISOR", "AUDITOR", "REVISOR")
                .requestMatchers("/views/proyectos/eliminar/**").hasAnyRole("ADMIN", "SUPERVISOR", "AUDITOR", "REVISOR")
                .requestMatchers("/views/proyectos/detalle/**").hasAnyRole("ADMIN", "SUPERVISOR", "AUDITOR", "REVISOR", "DESARROLLO")
                .anyRequest().authenticated()
            )
            .formLogin(formLogin -> formLogin
                .successHandler(loginSuccessMesage)
                .loginPage("/login")
                .permitAll()
            )
            .logout(logout -> logout.permitAll())
            .build();
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
