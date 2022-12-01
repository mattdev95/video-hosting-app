package com.mattdev95.videohostingapplication.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Optional<ApplicationUser> app = applicationUserRepository.findByUserName(username);
        Optional<ApplicationUser> fakeUsers = getApplicationUsers().stream().filter(applicationUser ->
                username.equals(applicationUser.getUsername())).findFirst();
        if(app.isPresent()) {
            return app.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        } else if(fakeUsers.isPresent()) {
            return fakeUsers.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        }
        return null;
    }

    private List<ApplicationUser> getApplicationUsers() {
        // this is a list of applicaiton users

        return Lists.newArrayList(
                new ApplicationUser("admin", bCryptPasswordEncoder.encode("password"), ApplicationUserRole.CREATOR),
                new ApplicationUser("consumer", bCryptPasswordEncoder.encode("password"), ApplicationUserRole.CONSUMER)
        );
    }

    public Boolean registerUser(AppUserRegistrationRequest appUserRegistrationRequest) {

        try {
            String password = appUserRegistrationRequest.getPassword();
            ApplicationUser applicationUser = new ApplicationUser(
                    appUserRegistrationRequest.getEmail(),
                    password,
                    ApplicationUserRole.CONSUMER
            );
            boolean userExists = applicationUserRepository.findByUserName(applicationUser.getUsername()).isPresent();
            if(userExists) {
                return false;
            }
            String encodePassword = bCryptPasswordEncoder.encode(password);
            applicationUser.setPassword(encodePassword);
            applicationUserRepository.save(applicationUser);
            return true;

        } catch (Exception e) {
            return false;
        }

    }

}










































