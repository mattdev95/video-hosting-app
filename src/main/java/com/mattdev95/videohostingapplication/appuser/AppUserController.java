package com.mattdev95.videohostingapplication.appuser;


import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value = "/api/v1.0/register")
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
