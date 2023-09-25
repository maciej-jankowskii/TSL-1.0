package com.tslcompany.cargo;

import lombok.extern.java.Log;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CargoRepository extends CrudRepository<Cargo, Long> {

    Cargo findById(Log id);
    void deleteById(Long id);


}
