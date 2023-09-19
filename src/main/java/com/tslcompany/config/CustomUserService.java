package com.tslcompany.config;

import com.tslcompany.user.UserDto;
import com.tslcompany.user.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserService implements UserDetailsService {

    private final UserService userService;

    public CustomUserService(UserService userService) {
        this.userService = userService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findUserDto(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("User with this e-mail address not found"));

    }

    private UserDetails createUserDetails(UserDto userDto){
        return User.builder()
                .username(userDto.getEmail())
                .password(userDto.getPassword())
                .roles(userDto.getRoles().toArray(String[]::new))
                .build();
    }


}
