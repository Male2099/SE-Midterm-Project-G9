package se.group9.gicCafe.security.login_security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
// import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import se.group9.gicCafe.constants.CONSTANT;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  // private final CustomUserDetailsService uService;

  @Bean
  public UserDetailsService userDetailsService() {
    return new CustomUserDetailsService();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    System.out.println(11212);
    http.authorizeHttpRequests(auth -> auth
        .requestMatchers("/").permitAll()
        .requestMatchers("/admin/**").hasAnyAuthority(CONSTANT.Admin_Role)
        .requestMatchers("/tables/**").hasAuthority(CONSTANT.Cashier_Role)
        .anyRequest().authenticated());

    http.csrf().disable();
    
    http.formLogin(login -> login
        .loginPage("/login")
        .usernameParameter("username")
        .successHandler(successHandler)
        .loginProcessingUrl("/login")
        .permitAll())
        .logout(logout -> logout.logoutUrl("/logout")
            .logoutSuccessUrl("/")
            .permitAll());
    return http.build();
  }

  @Autowired
  private LoginSuccessHandler successHandler;

}