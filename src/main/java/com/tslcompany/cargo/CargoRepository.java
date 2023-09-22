package com.tslcompany.cargo;

import lombok.extern.java.Log;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CargoRepository extends CrudRepository<Cargo, Long> {

    Cargo findById(Log id);




}
