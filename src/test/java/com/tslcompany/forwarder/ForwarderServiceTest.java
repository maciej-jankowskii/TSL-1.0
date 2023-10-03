package com.tslcompany.forwarder;

import com.tslcompany.cargo.Cargo;
import com.tslcompany.order.Order;
import com.tslcompany.order.OrderRepository;
import com.tslcompany.user.User;
import com.tslcompany.user.UserRepository;
import com.tslcompany.user.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ForwarderServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private ForwarderService forwarderService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllForwarders(){
        User forwarder1 = createUserWithRole("FORWARDER");
        User forwarder2 = createUserWithRole("FORWARDER");
        List<User> forwarders = Arrays.asList(forwarder1,forwarder2);


        Order order1 = createOrder(forwarder1);
        Order order2 = createOrder(forwarder1);
        Order order3 = createOrder(forwarder1);
        Order order4 = createOrder(forwarder2);
        Order order5 = createOrder(forwarder2);
        List<Order> orders = Arrays.asList(order1, order2, order3, order4, order5);

        when(userRepository.findAllUsersByRoles_Name("FORWARDER")).thenReturn(forwarders);
        when(orderRepository.findByUser(forwarder1)).thenReturn(Arrays.asList(order1,order2,order3));
        when(orderRepository.findByUser(forwarder2)).thenReturn(Arrays.asList(order4,order5));

        List<ForwarderDto> allForwarders = forwarderService.findAllForwarders();


        assertNotNull(allForwarders);
        assertEquals(2, allForwarders.size());
        assertEquals(forwarder1.getId(), allForwarders.get(0).getUserId());
        assertEquals(forwarder2.getId(), allForwarders.get(1).getUserId());
        assertEquals(BigDecimal.valueOf(1500), allForwarders.get(0).getTotalMargin());
        assertEquals(BigDecimal.valueOf(1000), allForwarders.get(1).getTotalMargin());
    }

    private Order createOrder(User forwarder) {
        Order order = new Order();
        Cargo cargo = new Cargo();

        cargo.setPrice(BigDecimal.valueOf(1000));
        order.setPrice(BigDecimal.valueOf(500));
        order.setCargo(cargo);
        order.setUser(forwarder);
        return order;
    }

    private User createUserWithRole(String forwarder) {
        User user = new User();
        UserRole role = new UserRole();
        role.setName(forwarder);
        user.getRoles().add(role);
        return user;
    }

}