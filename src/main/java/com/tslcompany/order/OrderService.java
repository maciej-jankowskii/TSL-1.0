package com.tslcompany.order;

import com.tslcompany.cargo.Cargo;
import com.tslcompany.cargo.CargoRepository;
import com.tslcompany.cargo.CargoService;
import com.tslcompany.customer.carrier.Carrier;
import com.tslcompany.customer.carrier.CarrierService;
import com.tslcompany.details.OrderStatus;
import com.tslcompany.user.User;
import com.tslcompany.user.UserMapper;
import com.tslcompany.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CargoService cargoService;
    private final CarrierService carrierService;
    private final UserMapper userMapper;

    private final UserService userService;
    private final CargoRepository cargoRepository;


    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, CargoService cargoService, CarrierService carrierService, UserMapper userMapper, UserService userService, CargoRepository cargoRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.cargoService = cargoService;
        this.carrierService = carrierService;
        this.userMapper = userMapper;
        this.userService = userService;
        this.cargoRepository = cargoRepository;
    }

    @Transactional
    public OrderDto createOrder(OrderDto orderDto, String username) {
        User user = userService.findUser(username).orElseThrow(() -> new NoSuchElementException("Brak użytkownika"));
        Order order = orderMapper.map(orderDto);
        Cargo cargo = cargoService.findCargo(orderDto.getCargoId()).orElseThrow(() -> new NoSuchElementException());
        Carrier carrier = carrierService.findById(orderDto.getCarrierId()).orElseThrow(() -> new NoSuchElementException());

        order.setUser(user);

        order.setCargo(cargo);
        order.setCarrier(carrier);
        order.setDateAdded(LocalDate.now());
        cargo.setAssignedToOrder(true);
        BigDecimal price = order.getPrice();
        BigDecimal balance = carrier.getBalance();
        carrier.setBalance(balance.add(price));

        cargoRepository.save(cargo);
        orderRepository.save(order);

        return orderMapper.map(order);
    }

    public List<Order> findAllOrders() {
        return (List<Order>) orderRepository.findAll();
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> findOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    @Transactional
    public void deleteById(Long id) {
        orderRepository.deleteById(id);

    }

    public OrderDto changeOrderStatus(Long id, OrderStatus orderStatus) {
        Order order = orderRepository.findById(id).orElseThrow();
        order.setOrderStatus(orderStatus);


        Order saved = orderRepository.save(order);
        return orderMapper.map(saved);
    }


}
