package com.tslcompany.customer.carrier;


import com.tslcompany.details.Address;
import com.tslcompany.details.AddressDto;
import com.tslcompany.details.AddressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CarrierService {

    private final CarrierRepository carrierRepository;
    private final CarrierMapper carrierMapper;
    private final AddressRepository addressRepository;

    public CarrierService(CarrierRepository carrierRepository, CarrierMapper carrierMapper, AddressRepository addressRepository) {
        this.carrierRepository = carrierRepository;
        this.carrierMapper = carrierMapper;
        this.addressRepository = addressRepository;
    }

    @Transactional
    public CarrierDto addCarrier(CarrierDto carrierDto) {
        Carrier carrier = carrierMapper.map(carrierDto);
        AddressDto addressDto = carrierDto.getAddress();

        Address address = new Address();
        address.setCity(addressDto.getCity());
        address.setPostalCode(addressDto.getPostalCode());
        address.setStreet(addressDto.getStreet());
        address.setHomeNo(addressDto.getHomeNo());
        address.setFlatNo(addressDto.getFlatNo());
        addressRepository.save(address);

        carrier.setAddress(address);
        Carrier saved = carrierRepository.save(carrier);

        return carrierMapper.map(saved);


    }

    public List<Carrier> findALlCarriers() {
        return (List<Carrier>) carrierRepository.findAll();
    }

    public Optional<Carrier> findById(Long id) {
        return carrierRepository.findById(id);
    }
}
