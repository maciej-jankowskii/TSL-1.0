package com.tslcompany.customer.carrier;

import com.tslcompany.customer.client.Client;
import com.tslcompany.customer.client.ClientDto;
import com.tslcompany.details.Address;
import com.tslcompany.details.AddressDto;
import com.tslcompany.details.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CarrierServiceTest {

    @Mock
    CarrierRepository carrierRepository;
    @Mock
    AddressRepository addressRepository;
    @Mock
    CarrierMapper carrierMapper;
    @InjectMocks
    CarrierService carrierService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllCarriers(){
        List<Carrier> carriers = new ArrayList<>();
        carriers.add(new Carrier());
        carriers.add(new Carrier());

        when(carrierRepository.findAll()).thenReturn(carriers);

        List<Carrier> result = carrierService.findALlCarriers();

        assertEquals(result, carriers);
        assertEquals(2, carriers.size());

    }

    @Test
    public void testFindById(){
        Carrier carrier = new Carrier();
        Long carrierId = 1L;
        carrier.setId(carrierId);

        when(carrierRepository.findById(carrierId)).thenReturn(Optional.of(carrier));
        Optional<Carrier> result = carrierService.findById(carrierId);

        assertEquals(Optional.of(carrier), result);

    }

    @Test
    public void testAddCarrier(){
        CarrierDto carrierDto = createTestCarrierDto();
        AddressDto addressDto = createTestAddressDto();
        carrierDto.setAddress(addressDto);

        Carrier carrier = createTestCarrier(carrierDto);
        Address address = createTestAddress();
        carrier.setAddress(address);

        when(carrierMapper.map(carrierDto)).thenReturn(carrier);
        when(addressRepository.save(any(Address.class))).thenReturn(address);
        when(carrierRepository.save(any(Carrier.class))).thenReturn(carrier);
        when(carrierMapper.map(carrier)).thenReturn(carrierDto);

        CarrierDto saved = carrierService.addCarrier(carrierDto);

        assertEquals(carrierDto, saved);
        verify(addressRepository, times(1)).save(any(Address.class));
        verify(carrierRepository, times(1)).save(any(Carrier.class));

    }



    private CarrierDto createTestCarrierDto() {
        CarrierDto carrierDto = new CarrierDto();
        carrierDto.setFullName("Test client");
        carrierDto.setShortName("Test");
        carrierDto.setVatNumber("PL4329854389");
        carrierDto.setDescription("Test");
        carrierDto.setTermOfPayment(30);
        return carrierDto;
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

    private Carrier createTestCarrier(CarrierDto carrierDto) {

        Carrier carrier = new Carrier();
        carrier.setFullName(carrierDto.getFullName());
        carrier.setShortName(carrierDto.getShortName());
        carrier.setVatNumber(carrierDto.getVatNumber());
        carrier.setDescription(carrierDto.getDescription());
        carrier.setTermOfPayment(carrierDto.getTermOfPayment());
        carrier.setBalance(BigDecimal.ZERO);
        return carrier;

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