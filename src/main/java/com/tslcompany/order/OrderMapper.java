package com.tslcompany.order;

import com.tslcompany.details.OrderStatus;
import com.tslcompany.truck.Truck;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {

    public OrderDto map(Order order) {
        OrderDto dto = new OrderDto();
        dto.setCargoId(order.getCargo().getId());
        dto.setCarrierId(order.getCarrier().getId());
        dto.setPrice(order.getPrice());
        dto.setTypeOfTruck(order.getTypeOfTruck().name());
        dto.setTruckNumbers(order.getTruckNumbers());
        dto.setUserId(order.getUser().getId()); // zmiany
        dto.setOrderStatus(order.getOrderStatus().name());
        return dto;
    }

    public Order map(OrderDto orderDto) {
        Order order = new Order();

        order.setPrice(orderDto.getPrice());
        order.setTypeOfTruck(Truck.valueOf(orderDto.getTypeOfTruck()));
        order.setTruckNumbers(orderDto.getTruckNumbers());
        order.setOrderStatus(OrderStatus.valueOf(orderDto.getOrderStatus()));
        return order;
    }
}
