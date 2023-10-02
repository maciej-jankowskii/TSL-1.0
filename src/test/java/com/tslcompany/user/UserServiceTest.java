package com.tslcompany.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserRoleRepository userRoleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, userRoleRepository, passwordEncoder);
    }

    @Test
    public void testFindUser() {
        //given
        String email = "test@tslcompany.com";
        User user = new User();
        user.setEmail(email);

        // when
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user));
        Optional<User> exampleUser = userService.findUser(email);

        //then
        assertThat(exampleUser.isPresent());
        assertEquals(email, user.getEmail());

    }

    @Test
    public void testRegister() {
        UserRegistrationDto dto = new UserRegistrationDto();
        dto.setFirstName("Test");
        dto.setLastName("Test");
        dto.setEmail("test@tslcompany.com");
        dto.setPassword("password");
        String hashedPassword = "hashedPassword";

        when(passwordEncoder.encode(dto.getPassword())).thenReturn(hashedPassword);

        userService.register(dto);

//        assertEquals(hashedPassword, dto.getPassword());

        verify(userRepository, times(1)).save(argThat(user -> {
            return user.getFirstName().equals(dto.getFirstName()) &&
                    user.getLastName().equals(dto.getLastName()) &&
                    user.getEmail().equals(dto.getEmail()) &&
                    user.getPassword().equals(hashedPassword);
        }));

    }

    @Test
    public void testFindAllForwarderEmail() {
        String forwarderRole = "FORWARDER";
        List<User> userList = new ArrayList<>();
        userList.add(createUser("test1@tslcompany.com", userRoleRepository, forwarderRole));
        userList.add(createUser("test2@tslcompany.com", userRoleRepository, forwarderRole));

        when(userRepository.findAllUsersByRoles_Name(forwarderRole)).thenReturn(userList);

        List<String> allForwarderEmail = userService.findAllForwarderEmail();

        assertEquals(2, allForwarderEmail.size());
        assertTrue(allForwarderEmail.contains("test1@tslcompany.com"));
        assertTrue(allForwarderEmail.contains("test2@tslcompany.com"));
    }


    @Test
    public void testFindAllBookkeepingEmail() {
        String bookkeepingRole = "ACCOUNTANT";
        List<User> userList = new ArrayList<>();
        userList.add(createUser("test1@tslcompany.com", userRoleRepository, bookkeepingRole));
        userList.add(createUser("test2@tslcompany.com", userRoleRepository, bookkeepingRole));
        when(userRepository.findAllUsersByRoles_Name(bookkeepingRole)).thenReturn(userList);

        List<String> allBookkeepingEmail = userService.findAllBookkeepingEmail();

        assertEquals(2, allBookkeepingEmail.size());
        assertTrue(allBookkeepingEmail.contains("test1@tslcompany.com"));
        assertTrue(allBookkeepingEmail.contains("test2@tslcompany.com"));
    }

    @Test
    public void testDeleteUser() {
        String email = "test@tslcompany.com";

        userService.deleteUserByEmail(email);

        verify(userRepository, times(1)).deleteUserByEmail(email);
    }

    @Test
    public void testFindUserWithoutRole() {
        List<User> users = new ArrayList<>();
        users.add(createUserWithoutRole("test1@tslcompany.com"));
        users.add(createUserWithoutRole("test2@tslcompany.com"));

        when(userRepository.findUserByRolesIsEmpty()).thenReturn(users);

        List<User> userWithoutRole = userService.findUserWithoutRole();

        assertEquals(2, userWithoutRole.size());

    }

    @Test
    public void testSetNewRole() {
        Long userId = 1L;
        String roleName = "ROLE";
        User userWithoutRole = createUserWithoutRole("test1@tslcompanny.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(userWithoutRole));

        UserRole userRole = new UserRole();
        userRole.setName(roleName);
        when(userRoleRepository.findByName(roleName)).thenReturn(Optional.of(userRole));

        userService.setNewRole(userId, roleName);

        assertTrue(userWithoutRole.getRoles().stream().anyMatch(uRole -> uRole.getName().equals(roleName)));
    }


    private User createUser(String email, UserRoleRepository userRoleRepository, String roleName) {
        User user = new User();
        user.setEmail(email);
        if (roleName != null) {
            Optional<UserRole> optionalRole = userRoleRepository.findByName(roleName);
            if (optionalRole.isPresent()) {
                UserRole role = optionalRole.get();
                user.getRoles().add(role);
            }

        }
        return user;
    }

    private User createUserWithoutRole(String email) {
        User user = new User();
        user.setEmail(email);
        return user;
    }
}