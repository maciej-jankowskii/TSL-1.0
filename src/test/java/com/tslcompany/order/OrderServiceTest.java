package com.tslcompany.order;

import com.tslcompany.cargo.Cargo;
import com.tslcompany.cargo.CargoRepository;
import com.tslcompany.cargo.CargoService;
import com.tslcompany.customer.carrier.Carrier;
import com.tslcompany.customer.carrier.CarrierRepository;
import com.tslcompany.customer.carrier.CarrierService;
import com.tslcompany.customer.client.Client;
import com.tslcompany.customer.client.ClientRepository;
import com.tslcompany.details.OrderStatus;
import com.tslcompany.user.User;
import com.tslcompany.user.UserMapper;
import com.tslcompany.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class OrderServiceTest {

    @Mock private  OrderRepository orderRepository;
    @Mock private  OrderMapper orderMapper;
    @Mock private  CargoService cargoService;
    @Mock private  CarrierService carrierService;
    @Mock private  UserMapper userMapper;

    @Mock private  UserService userService;
    @Mock private  CargoRepository cargoRepository;
    @Mock private  ClientRepository clientRepository;
    @Mock private  CarrierRepository carrierRepository;
    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindOrder(){
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Optional<Order> optionalOrder = orderService.findById(orderId);
        Order expectedOrder = optionalOrder.get();

        assertEquals(expectedOrder, order);

    }

    @Test
    public void testFindOrderWhenNotFound(){
        Long orderId = 1L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        Optional<Order> foundOrder = orderService.findById(orderId);

        assertFalse(foundOrder.isPresent());
    }

    @Test
    public void testFindOrderByUser(){
        User user = createTestUser();
        List<Order> userOrders = createTestUserOrder(user);

        when(orderRepository.findByUser(user)).thenReturn(userOrders);

        List<Order> foundOrders = orderService.findOrdersByUser(user);

        assertEquals(userOrders.size(), foundOrders.size());
    }

    @Test
    public void testFindOrderByUserWhenNoOrdersFound(){
        User user = createTestUser();
        when(orderRepository.findByUser(user)).thenReturn(Collections.emptyList());

        List<Order> foundOrders = orderService.findOrdersByUser(user);

        assertTrue(foundOrders.isEmpty());
    }

    @Test
    public void testFindOrderWithNoInvoice(){
        List<Order> allOrders = createTestOrdersWithInvoice();
        when(orderRepository.findAll()).thenReturn(allOrders);

        List<Order> ordersWithNoInvoice = orderService.findAllOrdersWithNoInvoice();
        long expectedNumber = ordersWithNoInvoice.stream().filter(order -> !order.isInvoiced()).count();

        assertTrue(ordersWithNoInvoice.stream().allMatch(order -> !order.isInvoiced()));
        assertEquals(expectedNumber, ordersWithNoInvoice.size());

    }

    @Test
    public void testCreateOrder(){
        OrderDto orderDto = createOrderDto();
        Order order = createTestOrder();
        User user = createTestUser();
        String email = user.getEmail();
        Cargo cargo = createTestCargo();
        Carrier carrier = createTestCarrier();


        BigDecimal initialBalance = carrier.getBalance();
        order.setCargo(cargo);
        order.setCarrier(carrier);


        when(userService.findUser(email)).thenReturn(Optional.of(user));
        when(orderMapper.map(orderDto)).thenReturn(order);
        when(cargoService.findCargo(orderDto.getCargoId())).thenReturn(Optional.of(cargo));
        when(carrierService.findById(orderDto.getCarrierId())).thenReturn(Optional.of(carrier));

        when(cargoRepository.save(cargo)).thenReturn(cargo);
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.map(order)).thenReturn(orderDto);

        OrderDto createdOrder = orderService.createOrder(orderDto, email);

        assertNotNull(createdOrder);


        BigDecimal updatedBalance = carrier.getBalance();
        assertEquals(initialBalance.add(order.getPrice()), updatedBalance);


    }

    @Test
    public void testChangeOrderStatus(){
        Order order = createTestOrder();
        OrderDto orderDto = createOrderDto();
        Long orderId = order.getId();

        OrderStatus newStatus = OrderStatus.ENDED;


        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.map(order)).thenReturn(orderDto);

        OrderDto updateOrder = orderService.changeOrderStatus(orderId, newStatus);

        assertEquals(newStatus, order.getOrderStatus());
        assertNotNull(updateOrder);
    }

    @Test
    public void testChangeOrderStatusWhenOrderIsInvoiced(){
        Order order = createTestOrder();
        OrderDto orderDto = createOrderDto();
        Long orderId = order.getId();
        order.setInvoiced(true);
        OrderStatus newStatus = OrderStatus.ON_LOADING;

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));


        assertThrows(IllegalStateException.class, () -> orderService.changeOrderStatus(orderId, newStatus));
    }

    // został test do zmiany salda jezeli jest anulowane, oraz edycja zlecenia





    private Order createTestOrder() {
        Order order = new Order();
        order.setId(1L);
        order.setPrice(BigDecimal.valueOf(1000));
        order.setOrderStatus(OrderStatus.ASSIGNED_TO_CARRIER);
        return order;

    }

    private Carrier createTestCarrier() {
        Carrier carrier = new Carrier();
        carrier.setId(1L);
        carrier.setBalance(BigDecimal.ZERO);
        return carrier;
    }

    private Cargo createTestCargo() {
        Cargo cargo = new Cargo();
        cargo.setId(1L);
        cargo.setClient(createTestClient());
        cargo.setPrice(BigDecimal.valueOf(1500));

        return cargo;
    }

    private Client createTestClient() {
        Client client = new Client();
        client.setId(1L);
        client.setBalance(BigDecimal.ZERO);
        return client;
    }

    private OrderDto createOrderDto() {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(1L);
        return orderDto;
    }

    private List<Order> createTestOrdersWithInvoice() {
        List<Order> orders = new ArrayList<>();
        Order order = new Order();
        order.setId(1L);
        order.setInvoiced(false);
        Order order1 = new Order();
        order1.setId(2L);
        order1.setInvoiced(false);
        Order order2 = new Order();
        order2.setId(3L);
        order2.setInvoiced(true);
        return orders;
    }


    private List<Order> createTestUserOrder(User user) {
        List<Order> orders = new ArrayList<>();
        Order order = new Order();
        order.setUser(user);
        order.setId(1L);
        Order order1 = new Order();
        order1.setUser(user);
        order1.setId(2L);

        orders.add(order);
        orders.add(order1);

        return orders;
    }

    private User createTestUser() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setEmail("test@tslcompany.com");
        user.setPassword("test");
        return user;
    }


}