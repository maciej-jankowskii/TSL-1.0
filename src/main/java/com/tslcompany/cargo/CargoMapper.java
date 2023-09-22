package com.tslcompany.cargo;

import org.springframework.stereotype.Service;

@Service
public class CargoMapper {

    public CargoDto map(Cargo cargo) {
        CargoDto dto = new CargoDto();
        dto.setCargoNumberFromCustomer(cargo.getCargoNumberFromCustomer());
        dto.setPrice(cargo.getPrice());
        dto.setDateAdded(cargo.getDateAdded());
        dto.setLoadingDate(cargo.getLoadingDate());
        dto.setUnloadingDate(cargo.getUnloadingDate());
        dto.setLoadingAddress(cargo.getLoadingAddress());
        dto.setUnloadingAddress(cargo.getUnloadingAddress());
        dto.setGoods(cargo.getGoods());
        dto.setDescription(cargo.getDescription());
        return dto;
    }

    public Cargo map(CargoDto cargoDto) {
        Cargo cargo = new Cargo();
        cargo.setCargoNumberFromCustomer(cargoDto.getCargoNumberFromCustomer());
        cargo.setPrice(cargoDto.getPrice());
        cargo.setDateAdded(cargoDto.getDateAdded());
        cargo.setLoadingDate(cargoDto.getLoadingDate());
        cargo.setUnloadingDate(cargoDto.getUnloadingDate());
        cargo.setLoadingAddress(cargoDto.getLoadingAddress());
        cargo.setUnloadingAddress(cargoDto.getUnloadingAddress());
        cargo.setGoods(cargoDto.getGoods());
        cargo.setDescription(cargoDto.getDescription());
        return cargo;

    }
}
