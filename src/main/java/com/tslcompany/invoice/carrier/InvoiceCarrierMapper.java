package com.tslcompany.invoice.carrier;

import com.tslcompany.customer.carrier.Carrier;
import com.tslcompany.customer.carrier.CarrierRepository;
import com.tslcompany.order.Order;
import com.tslcompany.order.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class InvoiceCarrierMapper {

    private final OrderRepository orderRepository;
    private final CarrierRepository carrierRepository;

    public InvoiceCarrierMapper(OrderRepository orderRepository, CarrierRepository carrierRepository) {
        this.orderRepository = orderRepository;
        this.carrierRepository = carrierRepository;
    }

    public InvoiceCarrierDto map(InvoiceFromCarrier invoice) {
        InvoiceCarrierDto dto = new InvoiceCarrierDto();
        dto.setInvoiceNumber(invoice.getInvoiceNumber());
        dto.setCarrierId(invoice.getCarrier().getId());
        dto.setOrderNumber(invoice.getOrder().getId());
        dto.setNettoValue(invoice.getNettoValue());
        dto.setBruttoValue(invoice.getBruttoValue());
        return dto;
    }

    public InvoiceFromCarrier map(InvoiceCarrierDto invoiceDto) {
        InvoiceFromCarrier invoice = new InvoiceFromCarrier();
        invoice.setInvoiceNumber(invoiceDto.getInvoiceNumber());
        Order order = orderRepository.findById(invoiceDto.getOrderNumber()).orElse(null);
        invoice.setOrder(order);
        Carrier carrier = carrierRepository.findById(invoiceDto.getCarrierId()).orElse(null);
        invoice.setCarrier(carrier);
        invoice.setNettoValue(invoiceDto.getNettoValue());
        invoice.setBruttoValue(invoiceDto.getBruttoValue());
        return invoice;
    }
}
