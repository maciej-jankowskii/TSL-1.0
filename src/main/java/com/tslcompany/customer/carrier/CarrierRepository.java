package com.tslcompany.customer.carrier;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CarrierRepository extends CrudRepository<Carrier, Long> {

    Optional<Carrier> findById(Long id);
}
