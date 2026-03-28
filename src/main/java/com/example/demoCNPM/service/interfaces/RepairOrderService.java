package com.example.demoCNPM.service.interfaces;

import com.example.demoCNPM.entity.RepairOrder;
import com.example.demoCNPM.enums.ItemType;

import java.util.List;

public interface RepairOrderService {

    RepairOrder save(RepairOrder order);

    List<RepairOrder> findAll();

    RepairOrder findById(Long id);


    void addService(Long orderId, Long serviceId, int quantity);

    void addPart(Long orderId, Long partId, int quantity);

    void finalizeQuotation(Long orderId);

    public void addItem(Long orderId, Long itemId, ItemType type, int quantity);
}