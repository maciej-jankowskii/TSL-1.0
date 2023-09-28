package com.tslcompany.cargo;

import com.tslcompany.customer.client.Client;
import com.tslcompany.customer.client.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CargoService {

    private final CargoRepository cargoRepository;
    private final ClientRepository clientRepository;
    private final CargoMapper cargoMapper;

    public CargoService(CargoRepository cargoRepository, ClientRepository clientRepository, CargoMapper cargoMapper) {
        this.cargoRepository = cargoRepository;
        this.clientRepository = clientRepository;
        this.cargoMapper = cargoMapper;
    }

    public List<Cargo> findAllCargos() {
        return (List<Cargo>) cargoRepository.findAll();

    }

    public List<Cargo> findCargosWithoutClientInvoice() {
        List<Cargo> allCargos = (List<Cargo>) cargoRepository.findAll();
        return allCargos.stream()
                .filter(cargo -> Boolean.FALSE.equals(cargo.isInvoicedForClient()))
                .collect(Collectors.toList());
    }

    public List<Cargo> findFreeCargos(){
        List<Cargo> allCargos = (List<Cargo>) cargoRepository.findAll();
        return allCargos.stream().filter(cargo -> !cargo.isAssignedToOrder()).collect(Collectors.toList());
    }

    public Optional<Cargo> findCargo(Long id) {
        return cargoRepository.findById(id);
    }
    public Cargo findCargoByCargoNumber(String cargoNumber){
        return cargoRepository.findByCargoNumberFromCustomer(cargoNumber);
    }

    @Transactional
    public void deleteById(Long id) {
        cargoRepository.deleteById(id);
    }


    @Transactional
    public CargoDto addCargo(CargoDto cargoDto) {
        Long clientId = cargoDto.getClientDto().getId();
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new NoSuchElementException("Brak klienta"));

        Cargo cargo = cargoMapper.map(cargoDto);
        cargo.setClient(client);
        cargo.setDateAdded(LocalDate.now());

        LocalDate loadingDate = cargo.getLoadingDate();
        LocalDate unloadingDate = cargo.getUnloadingDate();
        if (unloadingDate != null && unloadingDate.isBefore(loadingDate)){
            throw new IllegalArgumentException();
        }
        BigDecimal balance = client.getBalance();
        BigDecimal price = cargo.getPrice();
        client.setBalance(balance.add(price));

        Cargo saved = cargoRepository.save(cargo);

        return cargoMapper.map(saved);
    }

    @Transactional
    public CargoDto editCargo(Long id, CargoDto cargoDto){
        Cargo cargo = cargoRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Brak ładunku"));
        if (cargo == null) {
            throw new NoSuchElementException("Brak ładunku");
        }
        if (cargo.isAssignedToOrder() || cargo.isInvoicedForClient()){
            throw new IllegalStateException("Nie można edytować ładunku który został przypisany do zlecenia lub zafakturowany");
        }
            cargo.setCargoNumberFromCustomer(cargoDto.getCargoNumberFromCustomer());
            cargo.setPrice(cargoDto.getPrice());

        LocalDate loadingDate = cargo.getLoadingDate();
        LocalDate unloadingDate = cargo.getUnloadingDate();
        if (unloadingDate != null && unloadingDate.isBefore(loadingDate)){
            throw new IllegalArgumentException();
        }

            cargo.setLoadingDate(cargoDto.getLoadingDate());
            cargo.setUnloadingDate(cargoDto.getUnloadingDate());
            cargo.setLoadingAddress(cargoDto.getLoadingAddress());
            cargo.setUnloadingAddress(cargoDto.getUnloadingAddress());
            cargo.setGoods(cargoDto.getGoods());
            cargo.setDescription(cargo.getDescription());


        Cargo saved = cargoRepository.save(cargo);
        return cargoMapper.map(saved);
    }
}
