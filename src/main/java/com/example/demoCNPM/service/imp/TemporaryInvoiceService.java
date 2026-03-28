package com.example.demoCNPM.service.imp;

import com.example.demoCNPM.entity.TemporaryInvoice;
import com.example.demoCNPM.repository.TemporaryInvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemporaryInvoiceService {

    private final TemporaryInvoiceRepository invoiceRepository;

    public TemporaryInvoice findById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + id));
    }

    public TemporaryInvoice findByRepairOrderId(Long repairOrderId) {
        return invoiceRepository.findByRepairOrderId(repairOrderId);
    }
}
