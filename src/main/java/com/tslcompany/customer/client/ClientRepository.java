package com.tslcompany.customer.client;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Long> {
    Client findByFullName(String fullName);

}
