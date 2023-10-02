package com.tslcompany.order;

import com.tslcompany.cargo.CargoRepository;
import com.tslcompany.cargo.CargoService;
import com.tslcompany.customer.carrier.CarrierRepository;
import com.tslcompany.customer.carrier.CarrierService;
import com.tslcompany.customer.client.ClientRepository;
import com.tslcompany.user.UserMapper;
import com.tslcompany.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

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



}