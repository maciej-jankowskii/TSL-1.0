package com.tslcompany.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {

    private final String email;
    private final String password;
    private final Set<String> roles;
}
