package com.tslcompany.order;

import com.tslcompany.user.User;
import org.apache.catalina.LifecycleState;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {

    Optional<Order> findById(Long id);

    void deleteById(Long id);

    List<Order> findByUser(User user);
}
