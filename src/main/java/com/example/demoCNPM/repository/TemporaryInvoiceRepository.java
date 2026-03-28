package com.example.demoCNPM.repository;
import com.example.demoCNPM.entity.TemporaryInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemporaryInvoiceRepository extends JpaRepository<TemporaryInvoice, Long> {
    TemporaryInvoice findByRepairOrderId(Long repairOrderId);
}
