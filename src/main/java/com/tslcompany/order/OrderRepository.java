package com.tslcompany.order;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {

    Optional<Order> findById(Long id);

    void deleteById(Long id);
}
