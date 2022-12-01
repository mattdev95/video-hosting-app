package com.mattdev95.videohostingapplication.security;

import com.mattdev95.videohostingapplication.appuser.ApplicationUser;
import com.mattdev95.videohostingapplication.appuser.ApplicationUserRole;
import com.mattdev95.videohostingapplication.appuser.ApplicationUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    // ref: https://www.baeldung.com/spring-redirect-after-login

    SimpleUrlAuthenticationSuccessHandler userSuccessHandler =
            new SimpleUrlAuthenticationSuccessHandler("/dashboard");
    SimpleUrlAuthenticationSuccessHandler adminSuccessHandler =
            new SimpleUrlAuthenticationSuccessHandler("/admin");
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        try {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for(GrantedAuthority grantedAuthority : authorities) {
                String authorityNameRole = grantedAuthority.getAuthority();
                if(authorityNameRole.equals("CREATOR")) {
                    this.adminSuccessHandler.onAuthenticationSuccess(request, response, authentication);
                } else {
                    this.userSuccessHandler.onAuthenticationSuccess(request, response, authentication);
                }
            }
        } catch (Exception e) {
            System.out.println("The program has died, please get help. Call 911");
        }
    }
}
