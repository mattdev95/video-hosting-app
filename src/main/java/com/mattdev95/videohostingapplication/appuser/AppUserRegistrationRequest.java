package com.mattdev95.videohostingapplication.appuser;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AppUserRegistrationRequest {
    private final String password;
    private final String email;
}
