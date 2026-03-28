package com.example.demoCNPM.repository;

import com.example.demoCNPM.entity.Part;
import com.example.demoCNPM.entity.RepairOrder;
import com.example.demoCNPM.entity.ServiceClass;
import com.example.demoCNPM.entity.WorkOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkOrderItemRepository extends JpaRepository<WorkOrderItem, Long> {
    Optional<WorkOrderItem> findByRepairOrderAndService(
            RepairOrder order,
            ServiceClass service
    );
    Optional<WorkOrderItem> findByRepairOrderAndPart(
            RepairOrder order,
            Part part
    );
}
