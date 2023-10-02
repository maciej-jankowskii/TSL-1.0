package com.tslcompany.customer.client;

import com.tslcompany.details.Address;
import com.tslcompany.details.AddressDto;
import com.tslcompany.details.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ClientMapper clientMapper;
    @Mock
    private AddressRepository addressRepository;
    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void testFindAllClients() {
        List<Client> clients = new ArrayList<>();
        clients.add(new Client());
        clients.add(new Client());

        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> result = clientService.findAllClients();

        assertFalse(result.isEmpty());
        assertEquals(2, result.size());

    }

    @Test
    @Transactional
    public void testAddClient() {
        ClientDto clientDto = createTestClientDto();
        AddressDto addressDto = createTestAddressDto();
        clientDto.setAddress(addressDto);

        Client client = createTestClient(clientDto);
        Address address = createTestAddress();

        client.setAddress(address);

        when(clientMapper.map(clientDto)).thenReturn(client);
        when(addressRepository.save(any(Address.class))).thenReturn(address);
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        when(clientMapper.map(client)).thenReturn(clientDto);

        ClientDto saved = clientService.addClient(clientDto);

        assertEquals(clientDto, saved);

        verify(addressRepository, times(1)).save(any(Address.class));
        verify(clientRepository, times(1)).save(any(Client.class));


    }

    private ClientDto createTestClientDto() {
        ClientDto clientDto = new ClientDto();
        clientDto.setFullName("Test client");
        clientDto.setShortName("Test");
        clientDto.setVatNumber("PL4329854389");
        clientDto.setDescription("Test");
        clientDto.setTermOfPayment(30);
        return clientDto;
    }

    private AddressDto createTestAddressDto() {
        AddressDto addressDto = new AddressDto();
        addressDto.setCity("City");
        addressDto.setPostalCode("12345");
        addressDto.setStreet("Street");
        addressDto.setHomeNo("1");
        addressDto.setFlatNo("1");
        return addressDto;
    }

    private Client createTestClient(ClientDto clientDto) {

        Client client = new Client();
        client.setFullName(clientDto.getFullName());
        client.setShortName(clientDto.getShortName());
        client.setVatNumber(clientDto.getVatNumber());
        client.setDescription(clientDto.getDescription());
        client.setTermOfPayment(clientDto.getTermOfPayment());
        client.setBalance(BigDecimal.ZERO);
        return client;

    }

    private Address createTestAddress() {
        Address address = new Address();
        address.setCity("City");
        address.setPostalCode("12345");
        address.setStreet("Street");
        address.setHomeNo("1");
        address.setFlatNo("1");
        return address;
    }
}