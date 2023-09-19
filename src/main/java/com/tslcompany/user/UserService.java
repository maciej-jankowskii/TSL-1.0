package com.tslcompany.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private static final String FORWARDER_ROLE = "FORWARDER";
    private static final String MANAGEMENT_ROLE = "MANAGEMENT";
    private static final String BOOKKEEPING_ROLE = "BOOKKEEPING";


    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<UserDto> findUserDto(String email){
        return userRepository.findUserByEmail(email).map(UserMapper::map);
    }

    public void register(UserRegistrationDto register){
        User user = new User();
        user.setFirstName(register.getFirstName());
        user.setLastName(register.getLastName());
        user.setEmail(register.getEmail());
        String hashPassword = passwordEncoder.encode(register.getPassword());
        user.setPassword(hashPassword);
        Optional<UserRole> optionalRole = userRoleRepository.findByName(FORWARDER_ROLE);
        optionalRole.ifPresent(role -> user.getRoles().add(role));
        userRepository.save(user);
    }
}
