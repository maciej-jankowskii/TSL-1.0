package com.tslcompany.order;

import com.tslcompany.cargo.Cargo;
import com.tslcompany.cargo.CargoService;
import com.tslcompany.customer.carrier.Carrier;
import com.tslcompany.customer.carrier.CarrierService;
import com.tslcompany.details.OrderStatus;
import com.tslcompany.user.User;
import com.tslcompany.user.UserDto;
import com.tslcompany.user.UserMapper;
import com.tslcompany.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CargoService cargoService;
    private final CarrierService carrierService;
    private final UserMapper userMapper;

    private final UserService userService;



    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, CargoService cargoService, CarrierService carrierService, UserMapper userMapper, UserService userService) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.cargoService = cargoService;
        this.carrierService = carrierService;
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        Order order = orderMapper.map(orderDto);
        Cargo cargo = cargoService.findCargo(orderDto.getCargoId()).orElseThrow(() -> new NoSuchElementException());
        Carrier carrier = carrierService.findById(orderDto.getCarrierId()).orElseThrow(() -> new NoSuchElementException());


        order.setCargo(cargo);
        order.setCarrier(carrier);


        orderRepository.save(order);

        return orderMapper.map(order);
    }

    public List<Order> findAllOrders() {
        return (List<Order>) orderRepository.findAll();
    }

    public OrderDto changeOrderStatus(Long id, OrderStatus orderStatus){
        Order order = orderRepository.findById(id).orElseThrow();
        order.setOrderStatus(orderStatus);


        Order saved = orderRepository.save(order);
        return orderMapper.map(saved);
    }


}
