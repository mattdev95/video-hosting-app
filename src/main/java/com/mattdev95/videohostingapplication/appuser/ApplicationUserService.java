package com.mattdev95.videohostingapplication.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationUserService implements UserDetailsService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ApplicationUserRepository applicationUserRepository;

    @Autowired
    public ApplicationUserService(ApplicationUserRepository applicationUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public ApplicationUser loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<ApplicationUser> app = applicationUserRepository.findByUserName(username).orElseThrow(
//                () -> new UsernameNotFoundException("Username not found"));
        Optional<ApplicationUser> app = applicationUserRepository.findByUserName(username);
        if(app.isPresent()) {
            return app.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        } else if(getApplicationUser().getUsername().equals(username)) {
            return getApplicationUser();
        }
        return null;
    }

    private ApplicationUser getApplicationUser() {
        // this is a list of applicaiton users
        return new ApplicationUser("admin", bCryptPasswordEncoder.encode("password"), ApplicationUserRole.ADMIN);
    }
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
