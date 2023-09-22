package com.tslcompany.order;

import com.tslcompany.cargo.Cargo;
import com.tslcompany.cargo.CargoService;
import com.tslcompany.customer.carrier.Carrier;
import com.tslcompany.customer.carrier.CarrierService;
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


    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, CargoService cargoService, CarrierService carrierService) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.cargoService = cargoService;
        this.carrierService = carrierService;
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


}
