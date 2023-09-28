package com.tslcompany.forwarder;

import com.tslcompany.order.Order;
import com.tslcompany.order.OrderRepository;
import com.tslcompany.order.OrderService;
import com.tslcompany.user.User;
import com.tslcompany.user.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ForwarderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public ForwarderService(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public List<ForwarderDto> findAllForwarders() {
        List<User> forwarders = userRepository.findAllUsersByRoles_Name("FORWARDER");
        List<ForwarderDto> forwardersDto = new ArrayList<>();

        for (User forwarder : forwarders) {
            BigDecimal margin = calculateMargin(forwarder);
            ForwarderDto dto = new ForwarderDto();
            dto.setUserId(forwarder.getId());
            dto.setEmail(forwarder.getEmail());
            dto.setTotalMargin(margin);

            forwardersDto.add(dto);

        }
        return forwardersDto;
    }

    private BigDecimal calculateMargin(User forwarder) {
        List<Order> orders = orderRepository.findByUser(forwarder);
        BigDecimal totalMargin = BigDecimal.ZERO;

        for (Order order : orders) {
            BigDecimal cargoPrice = order.getCargo().getPrice();
            BigDecimal orderPrice = order.getPrice();
            BigDecimal margin = cargoPrice.subtract(orderPrice);
            totalMargin = totalMargin.add(margin);

        }
        return totalMargin;
    }
}
