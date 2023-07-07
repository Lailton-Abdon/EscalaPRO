package br.com.fabrica.gesplantao.secutiry;

import br.com.fabrica.gesplantao.enumeration.PerfilEnum;
import br.com.fabrica.gesplantao.services.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.http.HttpServletResponse;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String INTERNO = PerfilEnum.INTERNO.toString();
    private static final String PRECEPTOR = PerfilEnum.PRECEPTOR.toString();
    private static final String COORDENADOR = PerfilEnum.COORDENADOR.toString();


    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private JwtService jwtService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OncePerRequestFilter jwtFilter() {
        return new JwtAuthFilter(jwtService, usuarioService);
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioService).passwordEncoder(passwordEncoder());
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/usuarios/auth").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/usuarios/promover-usuario/{matricula}").hasAnyAuthority(COORDENADOR)
                .antMatchers(HttpMethod.POST, "/api/coordenador/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/internos/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/rotinas/cadastrar").permitAll()
                .antMatchers(HttpMethod.GET, "/api/usuarios/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/especialidade/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/internos/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/profissao/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/rotinas/cadastrar").permitAll()
                .antMatchers(HttpMethod.POST, "/api/preceptor/cadastrar").permitAll()
                .antMatchers(HttpMethod.GET, "/api/internos/**").hasAnyAuthority(INTERNO)
                .antMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .and().headers().frameOptions().sameOrigin()
                .and().authorizeRequests()
                .anyRequest().authenticated().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint((request, response, e) -> {
                    response.setContentType("application/json");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Token expirado: faça login novamente para obter um novo token!");
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setContentType("application/json");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("Acesso negado: Você não tem permissão para acessar esse serviço!");
                });
        ;
    }


}
