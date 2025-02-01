package com.customerloan.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private static final String[] AUTHENTICATED_ROLE_LIST = {"ADMIN", "CUSTOMER"};
	private static final String ADMIN_AUTHORIZED_ROLE_NAME= "ADMIN";
	private static final String CUSTOMER_AUTHORIZED_ROLE_NAME= "CUSTOMER";
	
	private static final String[] ACCESIBLE_WHITE_LIST = {"/login", "/error"};
	private static final String[] AUTHORIZED_PATH_LIST = {"/swagger-ui/**", "/v3/api-docs/**"};
	
	
	private static final String CUSTOMER_TRANSACTION_REST_PATTERN= "/customer/**";
	private static final String ADMIN_TRANSACTION_REST_PATTERN= "/admin/**";
	
	private static final String CUSTOM_LOGIN_PAGE_PATH= "/login";
	private static final String CUSTOM_LOGOUT_PAGE_PATH= "/logout";
	
	private static final String LOGIN_REDIRECTION_PATH= "/swagger-ui/index.html";
	private static final String LOGOUT_REDIRECTION_PATH= "/login?logout";
	
	private static final String DELETE_COOKIE_SESSION_ID= "JSESSIONID";
	

	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(ACCESIBLE_WHITE_LIST).permitAll() // Login sayfasına serbest erişim
                .requestMatchers(AUTHORIZED_PATH_LIST).authenticated() // Swagger için yetkilendirme
                .requestMatchers(CUSTOMER_TRANSACTION_REST_PATTERN).hasAnyRole(AUTHENTICATED_ROLE_LIST) // Customer endpointleri sadece customer'a açık
                .requestMatchers(ADMIN_TRANSACTION_REST_PATTERN).hasRole(ADMIN_AUTHORIZED_ROLE_NAME) // Admin endpointleri sadece adminlere açık                
                .anyRequest().authenticated()                
            )
            .formLogin(login -> login.loginPage(CUSTOM_LOGIN_PAGE_PATH).permitAll()
            							.defaultSuccessUrl(LOGIN_REDIRECTION_PATH, true)
            							
            		)
            .logout(logout -> logout
                .logoutUrl(CUSTOM_LOGOUT_PAGE_PATH)
                .logoutSuccessUrl(LOGOUT_REDIRECTION_PATH)
                .invalidateHttpSession(true)
                .deleteCookies(DELETE_COOKIE_SESSION_ID)
                .permitAll() 
                
            ).csrf(csrf -> csrf.disable());

        return http.build();
    }
	
	
    
    @Bean
    protected UserDetailsService defineUsers() {  //below is example for our demo project. Normally, authorized user details must be collected with encryption in db or cyberArk related platform
        UserDetails admin = User.withUsername("admin")
        						.password(passwordEncoder().encode("admin123"))
        						.roles(ADMIN_AUTHORIZED_ROLE_NAME)
        						.build();

        UserDetails customer = User.withUsername("customer")
								.password(passwordEncoder().encode("customer123"))
								.roles(CUSTOMER_AUTHORIZED_ROLE_NAME)
								.build();

        return new InMemoryUserDetailsManager(admin, customer);
    }
    
    @Bean
    protected PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
}