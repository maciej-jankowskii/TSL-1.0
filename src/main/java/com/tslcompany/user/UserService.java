package com.tslcompany.user;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final String FORWARDER_ROLE = "FORWARDER";
    private static final String MANAGEMENT_ROLE = "MANAGEMENT";
    private static final String BOOKKEEPING_ROLE = "ACCOUNTANT";


    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<UserDto> findUserDto(String email) {
        return userRepository.findUserByEmail(email).map(UserMapper::map);
    }

    public Optional<User> findUser(String email){
        return userRepository.findUserByEmail(email);
    }

    public void register(UserRegistrationDto register) {
        User user = new User();
        user.setFirstName(register.getFirstName());
        user.setLastName(register.getLastName());
        user.setEmail(register.getEmail());
        String hashPassword = passwordEncoder.encode(register.getPassword());
        user.setPassword(hashPassword);
        userRepository.save(user);
    }

    public List<String> findAllForwarderEmail() {
        return userRepository.findAllUsersByRoles_Name(FORWARDER_ROLE)
                .stream()
                .map(User::getEmail)
                .toList();
    }

    public List<String> findAllBookkeepingEmail() {
        return userRepository.findAllUsersByRoles_Name(BOOKKEEPING_ROLE)
                .stream()
                .map(User::getEmail)
                .toList();
    }

    @Transactional
    public void deleteUserByEmail(String email) {
        userRepository.deleteUserByEmail(email);
    }

    public List<User> findUserWithoutRole() {
        return userRepository.findUserByRolesIsEmpty();
    }

    @Transactional
    public void setNewRole(Long userId, String roleName) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Optional<UserRole> optionalRole = userRoleRepository.findByName(roleName);
        if (optionalRole.isPresent()) {
            UserRole role = optionalRole.get();
            user.getRoles().add(role);
            userRepository.save(user);
        } else {
            throw new EntityNotFoundException("Role not found");
        }
    }
}
