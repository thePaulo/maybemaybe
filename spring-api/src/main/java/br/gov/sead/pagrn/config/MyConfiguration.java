package br.gov.sead.pagrn.config;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakPreAuthActionsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@KeycloakConfiguration
//@ComponentScan(basePackageClasses = KeycloakSecurityComponents.class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MyConfiguration extends KeycloakWebSecurityConfigurerAdapter
{
    /**
     * Registers the KeycloakAuthenticationProvider with the authentication manager.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(keycloakAuthenticationProvider());
    }

    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**"
            // other public endpoints of your API may be appended to this array
    };

    /**
     * Defines the session authentication strategy.
     */
    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(buildSessionRegistry());
    }

    @Bean
    public KeycloakConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    @Bean
    protected SessionRegistry buildSessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        super.configure(http);
        http
                .csrf().disable()
                .cors(withDefaults())
                .authorizeRequests()
                //.antMatchers("/admin*").hasRole("ADMIN")
                //.antMatchers("/funcoes**").hasRole("funcao")
                .antMatchers(HttpMethod.GET,"/vinculos/cpf/**").permitAll()
                .antMatchers(HttpMethod.GET,"/vinculos/matricula/**").permitAll()

                .antMatchers(HttpMethod.GET,"/cargos").permitAll()
                .antMatchers(HttpMethod.GET,"/uo").permitAll()
                .antMatchers(HttpMethod.GET,"/nivelcargo").permitAll()

                .antMatchers(HttpMethod.GET,"/servidores").permitAll()
                .antMatchers(HttpMethod.POST,"/nomeacoes").hasAuthority("nomeacao")
                .antMatchers(AUTH_WHITELIST).permitAll()
                //.anyRequest().authenticated();
                //.antMatchers(HttpMethod.POST,"/funcoes").hasAuthority("funcao")
                //.antMatchers(HttpMethod.POST,"/nomeacoes").hasAuthority("nomeacao")
                //.antMatchers(HttpMethod.POST,"/exoneracoes").hasAuthority("exoneracao")
                .anyRequest().permitAll();
    }
}