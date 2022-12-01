package com.mattdev95.videohostingapplication.appuser;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1.0/register")
public class AppUserController {
    private ApplicationUserService applicationUserService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody AppUserRegistrationRequest appUserRegistrationRequest) {
        Boolean registered = applicationUserService.registerUser(appUserRegistrationRequest);
        if(registered) {
            return ResponseEntity.created(URI.create("")).body("The user has been created");
        }
        return ResponseEntity.badRequest().body("The user could not be registered");
    }
}
