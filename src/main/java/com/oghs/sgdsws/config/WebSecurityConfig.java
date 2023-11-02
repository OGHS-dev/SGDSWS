package com.oghs.sgdsws.config;

import java.util.List;

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

    private static final String QUERY_USERS_BY_USERNAME = "SELECT u.NOMBRE_USUARIO, u.CONTRASENA, u.ESTATUS FROM TBL_USUARIO u WHERE NOMBRE_USUARIO = ? AND ESTATUS = 1";
    private static final String QUERY_ROLES_BY_USERNAME = "SELECT u.NOMBRE_USUARIO, r.DESCRIPCION FROM TBL_ROL r INNER JOIN TBL_USUARIO u ON r.ID_ROL = u.ID_ROL WHERE u.NOMBRE_USUARIO = ?";
    private static final List<String> listaRoles = List.of("ADMIN", "SUPERVISOR", "AUDITOR", "REVISOR", "DESARROLLO");
    
    private final DataSource dataSource;

    private final BCryptPasswordEncoder passEncoder;

    public WebSecurityConfig(DataSource dataSource, BCryptPasswordEncoder passEncoder) {
        this.dataSource = dataSource;
        this.passEncoder = passEncoder;
    }

    // @Override
    // public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //     registry
    //         .addResourceHandler("/webjars/**")
    //         .addResourceLocations("/webjars/");
    // }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, LoginSuccessMesage loginSuccessMesage) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable())
            .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                .requestMatchers("/login", "/", "/home", "/index", "/img/**", "/webjars/**").permitAll()

                .requestMatchers("/views/usuarios/").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2), listaRoles.get(3))
                .requestMatchers("/views/usuarios/crear").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2))
                .requestMatchers("/views/usuarios/guardar").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2))
                .requestMatchers("/views/usuarios/editar/**").hasAnyRole(listaRoles.get(0), listaRoles.get(1))
                .requestMatchers("/views/usuarios/eliminar/**").hasAnyRole(listaRoles.get(0))
                .requestMatchers("/views/usuarios/perfil").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2), listaRoles.get(3), listaRoles.get(4))

                .requestMatchers("/views/roles/").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2), listaRoles.get(3))
                .requestMatchers("/views/roles/crear").hasAnyRole(listaRoles.get(0))
                .requestMatchers("/views/roles/guardar").hasAnyRole(listaRoles.get(0))
                .requestMatchers("/views/roles/editar/**").hasAnyRole(listaRoles.get(0))
                .requestMatchers("/views/roles/eliminar/**").hasAnyRole(listaRoles.get(0))

                .requestMatchers("/views/proyectos/").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2), listaRoles.get(3), listaRoles.get(4))
                .requestMatchers("/views/proyectos/crear").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2), listaRoles.get(3))
                .requestMatchers("/views/proyectos/guardar").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2), listaRoles.get(3))
                .requestMatchers("/views/proyectos/editar/**").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2))
                .requestMatchers("/views/proyectos/eliminar/**").hasAnyRole(listaRoles.get(0))
                .requestMatchers("/views/proyectos/detalle/**").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2), listaRoles.get(3), listaRoles.get(4))
                .requestMatchers("/views/proyectos/detalle/actualizar/**").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2))
                .requestMatchers("/views/proyectos/detalle/descargarArchivo").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2), listaRoles.get(3), listaRoles.get(4))
                .requestMatchers("/views/proyectos/detalle/eliminarArchivo").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2), listaRoles.get(3), listaRoles.get(4))
                .requestMatchers("/views/proyectos/detalle/guardar").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2), listaRoles.get(3), listaRoles.get(4))
                .requestMatchers("/views/proyectos/detalle/obtener").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2), listaRoles.get(3), listaRoles.get(4))
                .requestMatchers("/views/proyectos/detalle/generarReporte").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2), listaRoles.get(3))

                .requestMatchers("/views/modulos/").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2), listaRoles.get(3))
                .requestMatchers("/views/modulos/crear").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2))
                .requestMatchers("/views/modulos/guardar").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2))
                .requestMatchers("/views/modulos/editar/**").hasAnyRole(listaRoles.get(0), listaRoles.get(1))
                .requestMatchers("/views/modulos/eliminar/**").hasAnyRole(listaRoles.get(0))

                .requestMatchers("/views/hallazgos/").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2), listaRoles.get(3))
                .requestMatchers("/views/hallazgos/crear").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2))
                .requestMatchers("/views/hallazgos/guardar").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2))
                .requestMatchers("/views/hallazgos/editar/**").hasAnyRole(listaRoles.get(0), listaRoles.get(1))
                .requestMatchers("/views/hallazgos/eliminar/**").hasAnyRole(listaRoles.get(0))

                .requestMatchers("/views/incidentes/").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2), listaRoles.get(3))
                .requestMatchers("/views/incidentes/crear").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2))
                .requestMatchers("/views/incidentes/guardar").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2))
                .requestMatchers("/views/incidentes/editar/**").hasAnyRole(listaRoles.get(0), listaRoles.get(1))
                .requestMatchers("/views/incidentes/eliminar/**").hasAnyRole(listaRoles.get(0))

                .requestMatchers("/views/categorias/").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2), listaRoles.get(3))
                .requestMatchers("/views/categorias/crear").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2))
                .requestMatchers("/views/categorias/guardar").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2))
                .requestMatchers("/views/categorias/editar/**").hasAnyRole(listaRoles.get(0), listaRoles.get(1))
                .requestMatchers("/views/categorias/eliminar/**").hasAnyRole(listaRoles.get(0))
                
                .requestMatchers("/views/prioridades/").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2), listaRoles.get(3))
                .requestMatchers("/views/prioridades/crear").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2))
                .requestMatchers("/views/prioridades/guardar").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2))
                .requestMatchers("/views/prioridades/editar/**").hasAnyRole(listaRoles.get(0), listaRoles.get(1))
                .requestMatchers("/views/prioridades/eliminar/**").hasAnyRole(listaRoles.get(0))

                .requestMatchers("/views/impactos/").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2), listaRoles.get(3))
                .requestMatchers("/views/impactos/crear").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2))
                .requestMatchers("/views/impactos/guardar").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2))
                .requestMatchers("/views/impactos/editar/**").hasAnyRole(listaRoles.get(0), listaRoles.get(1))
                .requestMatchers("/views/impactos/eliminar/**").hasAnyRole(listaRoles.get(0))

                .requestMatchers("/views/nivelesRiesgo/").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2), listaRoles.get(3))
                .requestMatchers("/views/nivelesRiesgo/crear").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2))
                .requestMatchers("/views/nivelesRiesgo/guardar").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2))
                .requestMatchers("/views/nivelesRiesgo/editar/**").hasAnyRole(listaRoles.get(0), listaRoles.get(1))
                .requestMatchers("/views/nivelesRiesgo/eliminar/**").hasAnyRole(listaRoles.get(0))

                .requestMatchers("/views/estadosProyecto/").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2), listaRoles.get(3))
                .requestMatchers("/views/estadosProyecto/crear").hasAnyRole(listaRoles.get(0))
                .requestMatchers("/views/estadosProyecto/guardar").hasAnyRole(listaRoles.get(0))
                .requestMatchers("/views/estadosProyecto/editar/**").hasAnyRole(listaRoles.get(0))
                .requestMatchers("/views/estadosProyecto/eliminar/**").hasAnyRole(listaRoles.get(0))

                .requestMatchers("/views/estadosBitacoraProyecto/").hasAnyRole(listaRoles.get(0), listaRoles.get(1), listaRoles.get(2), listaRoles.get(3))
                .requestMatchers("/views/estadosBitacoraProyecto/crear").hasAnyRole(listaRoles.get(0))
                .requestMatchers("/views/estadosBitacoraProyecto/guardar").hasAnyRole(listaRoles.get(0))
                .requestMatchers("/views/estadosBitacoraProyecto/editar/**").hasAnyRole(listaRoles.get(0))
                .requestMatchers("/views/estadosBitacoraProyecto/eliminar/**").hasAnyRole(listaRoles.get(0))
                
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
