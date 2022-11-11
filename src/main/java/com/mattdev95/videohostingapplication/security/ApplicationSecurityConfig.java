package com.mattdev95.videohostingapplication.security;

import com.mattdev95.videohostingapplication.appuser.ApplicationUserService;
import com.mattdev95.videohostingapplication.rest.RestEntryAuthenticationPoint;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class ApplicationSecurityConfig {

    private final PasswordEncoder passwordEncoder;
//    private final RestEntryAuthenticationPoint restEntryAuthPoint;
    private final ApplicationUserService applicationUserService;

//    @Autowired
//    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, RestEntryAuthPoint restEntryAuthPoint) {
//        this.passwordEncoder = passwordEncoder;
//        this.restEntryAuthPoint = restEntryAuthPoint;
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(applicationUserService)
                .passwordEncoder(passwordEncoder);

        AuthenticationManager authenticationManager
                = authenticationManagerBuilder.build();
        // need to change the layout of this slightly
//        http.csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .authorizeHttpRequests()
//                .antMatchers("/", "index", "/css/*", "/js/").permitAll()
//                .antMatchers("/api/v*/registration/**")
//                .permitAll()
//                .anyRequest()
//                .authenticated().and()
//                .exceptionHandling()
//                .authenticationEntryPoint(restEntryAuthPoint)
//                .and()
//                .formLogin()
//                .loginPage("/login").permitAll()
//
//                .defaultSuccessUrl("/courses", true)
//                .and()
//
//                .logout()
//                .logoutUrl("/logout")
//                // you should be using post for best practice - if you are using csrf delete the line below
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/login?logout").permitAll()
//                .clearAuthentication(true)
//                .invalidateHttpSession(true).and(). authenticationManager(authenticationManager);
        http.csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                // want to permit all
                // could allow only what the client can access
                .antMatchers("/api/v*/registration/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin().and().authenticationManager(authenticationManager);

        return http.build();


    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

//


}
