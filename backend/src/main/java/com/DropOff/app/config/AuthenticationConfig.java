package com.DropOff.app.config;

import com.DropOff.app.repository.Authentication.UserRepository;
import com.DropOff.app.service.Authentication.AuthenticationFilter;
import com.DropOff.app.service.Authentication.LogoutHandlerServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.DropOff.app.utility.Const.AUTH_EXCEPT_PATHS;

/*
* Reference: https://www.marcobehler.com/guides/spring-security
* Reference: https://spring.io/guides/gs/securing-web/
* Reference: https://www.toptal.com/spring/spring-security-tutorial
*/

@EnableWebSecurity
@Configuration
public class AuthenticationConfig extends WebSecurityConfigurerAdapter {
     @Autowired
     AuthenticationFilter authFilter;

     @Autowired
     UserRepository userRepository;

     @Autowired
     LogoutHandlerServiceImpl logoutService;

     /**
      * Configure the http security
.
      */
     @Override
     protected void configure(HttpSecurity http) throws Exception {
         CorsConfigurer<HttpSecurity> buildConfig  = http.cors();
         HttpSecurity config = buildConfig.and().csrf().disable();
         config.httpBasic().disable();
         config.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
         config.authorizeRequests().antMatchers(AUTH_EXCEPT_PATHS).permitAll().anyRequest().authenticated();
         config.logout().logoutUrl("/logout").addLogoutHandler(logoutService).logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
         config.authenticationProvider(authProvider()).addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
     }

     /**
      * Configure the web security
      */
     @Override
     public void configure(WebSecurity web) throws Exception {
          web.ignoring().antMatchers("/chatSocket/**");
          web.ignoring().antMatchers("/notificationSocket/**");
          super.configure(web);
     }

     /**
      * Configure Authentication manager
      */
     @Bean
     public AuthenticationManager authManager(AuthenticationConfiguration configuration) throws Exception {
          return configuration.getAuthenticationManager();
     }

     /**
      * User detail service
      */
     @Bean
     public UserDetailsService userDetails() {
          return new UserDetailsService() {
               @Override
               public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                    return userRepository.findByEmail(username);
               }
          };
     }

     /**
      * Authentication provider
      */
     @Bean
     public AuthenticationProvider authProvider() {
          DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
          provider.setUserDetailsService(userDetails());
          provider.setPasswordEncoder(new BCryptPasswordEncoder());
          System.out.println(provider.getClass());
          return provider;
     }

}
