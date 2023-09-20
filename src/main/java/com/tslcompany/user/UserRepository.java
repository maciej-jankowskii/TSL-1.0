package com.tslcompany.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findUserByEmail(String email);
    List<User> findAllUsersByRoles_Name (String role);

    void deleteUserByEmail(String email);

    List<User> findUserByRolesIsEmpty();


}
