package com.tslcompany.invoice.carrier;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface InvoiceCarrierRepository extends CrudRepository<InvoiceFromCarrier, Long> {



}
