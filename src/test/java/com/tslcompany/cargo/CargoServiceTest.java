package com.tslcompany.cargo;

import com.tslcompany.customer.client.Client;
import com.tslcompany.customer.client.ClientDto;
import com.tslcompany.customer.client.ClientRepository;
import com.tslcompany.details.Address;
import com.tslcompany.details.AddressDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CargoServiceTest {

    @Mock
    private CargoMapper cargoMapper;
    @Mock
    private CargoRepository cargoRepository;
    @Mock
    private ClientRepository clientRepository;
    @InjectMocks
    private CargoService cargoService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllCargos(){
        List<Cargo> cargos = new ArrayList<>();
        cargos.add(new Cargo());

        when(cargoRepository.findAll()).thenReturn(cargos);

        List<Cargo> result = cargoService.findAllCargos();

        assertEquals(cargos, result);
    }

    @Test
    public void testFindAllCargosWithoutInvoice(){
        List<Cargo> cargos = new ArrayList<>();

        Cargo cargo1 = new Cargo();
        cargo1.setInvoicedForClient(true);
        Cargo cargo2 = new Cargo();
        cargo2.setInvoicedForClient(false);

        cargos.add(cargo1);
        cargos.add(cargo2);

        when(cargoRepository.findAll()).thenReturn(cargos);

        List<Cargo> result = cargoService.findCargosWithoutClientInvoice();

        List<Cargo> expected = cargos.stream().filter(cargo -> !cargo.isInvoicedForClient()).collect(Collectors.toList());

        assertEquals(expected, result);
    }

    @Test
    public void testFindAllFreeCargos(){
        List<Cargo> cargos = new ArrayList<>();

        Cargo cargo1 = new Cargo();
        cargo1.setAssignedToOrder(true);
        Cargo cargo2 = new Cargo();
        cargo2.setAssignedToOrder(false);

        when(cargoRepository.findAll()).thenReturn(cargos);

        List<Cargo> result = cargoService.findFreeCargos();

        List<Cargo> expected = cargos.stream().filter(cargo -> !cargo.isAssignedToOrder()).collect(Collectors.toList());

        assertEquals(expected, result);
    }

    @Test
    public void testFindCargo(){
        Cargo cargo = new Cargo();
        Long cargoId = 1L;
        cargo.setId(cargoId);

        when(cargoRepository.findById(cargoId)).thenReturn(Optional.of(cargo));

        Optional<Cargo> optionalCargo = cargoService.findCargo(cargoId);
        Cargo resultCargo = optionalCargo.orElse(null);

        assertEquals(cargo, resultCargo);
    }

    @Test
    public void testDeleteCargoById(){
        Cargo cargo = new Cargo();
        Long cargoId = 1L;
        cargo.setId(1L);

        cargoService.deleteById(cargoId);

        verify(cargoRepository, times(1)).deleteById(cargoId);
    }

    @Test
    public void testAddCargo(){

        CargoDto cargoDto = createTestCargoDto();
        ClientDto clientDto = createTestClientDto();

        Cargo cargo = createTestCargo(cargoDto);
        Client client = createTestClient(clientDto);
        Address address = createTestAddress();
        client.setAddress(address);
        cargo.setPrice(cargoDto.getPrice());

        BigDecimal initialBalance = client.getBalance();


        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
        when(cargoMapper.map(cargoDto)).thenReturn(cargo);
        when(cargoRepository.save(any(Cargo.class))).thenReturn(cargo);
        when(cargoMapper.map(cargo)).thenReturn(cargoDto);


        CargoDto saved = cargoService.addCargo(cargoDto);

        BigDecimal updatedBalance = client.getBalance();
        BigDecimal expectedUpdateBalance = initialBalance.add(cargoDto.getPrice());


        assertNotNull(saved);
        assertEquals(expectedUpdateBalance, updatedBalance);


    }

    private ClientDto createTestClientDto() {
        ClientDto clientDto = new ClientDto();
        clientDto.setId(1L);
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
        client.setId(clientDto.getId());
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

    private CargoDto createTestCargoDto() {
        CargoDto cargoDto = new CargoDto();
        cargoDto.setId(1L);
        cargoDto.setCargoNumberFromCustomer("12345");
        cargoDto.setPrice(BigDecimal.valueOf(1000));
        cargoDto.setLoadingDate(LocalDate.now());
        cargoDto.setUnloadingDate(LocalDate.now().plusDays(1));
        cargoDto.setClientDto(createTestClientDto());
        cargoDto.setLoadingAddress("Loading Address");
        cargoDto.setUnloadingAddress("Unloading Address");
        cargoDto.setGoods("Goods");
        cargoDto.setDescription("Cargo Description");
        return cargoDto;
    }

    private Cargo createTestCargo(CargoDto cargoDto) {
        Cargo cargo = new Cargo();
        cargo.setId(cargoDto.getId());
        cargo.setCargoNumberFromCustomer(cargoDto.getCargoNumberFromCustomer());
        cargo.setLoadingDate(cargoDto.getLoadingDate());
        cargo.setUnloadingDate(cargoDto.getUnloadingDate());
        cargo.setLoadingAddress(cargoDto.getLoadingAddress());
        cargo.setUnloadingAddress(cargoDto.getUnloadingAddress());
        cargo.setGoods(cargoDto.getGoods());
        cargo.setDescription(cargoDto.getDescription());
        cargo.setAssignedToOrder(cargoDto.isAssignedToOrder());
        cargo.setInvoicedForClient(cargoDto.isInvoicedForClient());
        return cargo;
    }




}