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
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    SimpleUrlAuthenticationSuccessHandler userSuccessHandler =
            new SimpleUrlAuthenticationSuccessHandler("/videodashboard");
    SimpleUrlAuthenticationSuccessHandler adminSuccessHandler =
            new SimpleUrlAuthenticationSuccessHandler("/admindashboard");

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if (authorityName.equals("CREATOR")) {
                // if the user is an ADMIN delegate to the adminSuccessHandler
                this.adminSuccessHandler.onAuthenticationSuccess(request, response, authentication);
                return;
            }
        }
        // if the user is not an admin delegate to the userSuccessHandler
        this.userSuccessHandler.onAuthenticationSuccess(request, response, authentication);
    }

//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
////        handleRequests(request, response, authentication);
////        clearAuthenticationAttributes(request);
//
//        String redirectURL = request.getContextPath();
//
////        if (userDetails.getAuthorities().stream().filter(user -> user.equals(ApplicationUserRole.CONSUMER))) {
////            redirectURL = "sales_home";
////        } else if (userDetails.hasRole("Editor")) {
////            redirectURL = "editor_home";
////        } else if (userDetails.hasRole("Shipper")) {
////            redirectURL = "shipper_home";
////        }
//        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        for (GrantedAuthority grantedAuthority : authorities) {
//            String authorityName = grantedAuthority.getAuthority();
//            if(authorityName.equals("CREATOR")) {
//                redirectURL = "admindashboard";
//            } else {
//                redirectURL = "videodashboard";
//            }
//        }
//
//        response.sendRedirect(redirectURL);
//    }

//    protected void handleRequests(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
//        String targetUrl = determineTargetUrl(authentication);
//        redirectStrategy.sendRedirect(request, response, targetUrl);
//
//    }
//    protected String determineTargetUrl(final Authentication authentication) {
//
//        Map<String, String> roleTargetUrlMap = new HashMap<>();
//        roleTargetUrlMap.put("CUSTOMER", "/videodashboard");
//        roleTargetUrlMap.put("CREATOR", "/admindashboard");
//        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        for (GrantedAuthority grantedAuthority : authorities) {
//            String authorityName = grantedAuthority.getAuthority();
//            if(roleTargetUrlMap.containsKey(authorityName)) {
//                return roleTargetUrlMap.get(authorityName);
//            }
//        }
//        throw new IllegalStateException();
//    }
//
//    protected void clearAuthenticationAttributes(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (session == null) {
//            return;
//        }
//        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
//    }



}
