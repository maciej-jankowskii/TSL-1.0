package com.tslcompany.cargo;

import com.tslcompany.customer.client.Client;
import com.tslcompany.customer.client.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    public Optional<Cargo> findCargo(Long id) {
        return cargoRepository.findById(id);
    }


    @Transactional
    public CargoDto addCargo(CargoDto cargoDto) {
        Long clientId = cargoDto.getClientDto().getId();
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new NoSuchElementException("Brak klienta"));

        Cargo cargo = cargoMapper.map(cargoDto);
        cargo.setClient(client);


        Cargo saved = cargoRepository.save(cargo);
        return cargoMapper.map(saved);
    }
}
