package com.example.demoCNPM.service.impl;

import com.example.demoCNPM.entity.Part;
import com.example.demoCNPM.entity.RepairOrder;
import com.example.demoCNPM.entity.ServiceClass;
import com.example.demoCNPM.entity.WorkOrderItem;
import com.example.demoCNPM.enums.ItemType;
import com.example.demoCNPM.repository.PartRepository;
import com.example.demoCNPM.repository.RepairOrderRepository;
import com.example.demoCNPM.repository.ServiceRepository;
import com.example.demoCNPM.repository.WorkOrderItemRepository;
import com.example.demoCNPM.service.interfaces.RepairOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RepairOrderServiceImpl implements RepairOrderService {

    private final RepairOrderRepository repairOrderRepository;

    private final ServiceRepository serviceRepository;
    private final PartRepository partRepository;
    private final WorkOrderItemRepository workOrderItemRepository;

    @Override
    public RepairOrder save(RepairOrder order) {
        return repairOrderRepository.save(order);
    }

    @Override
    public List<RepairOrder> findAll() {
        return repairOrderRepository.findAll();
    }

    @Override
    public RepairOrder findById(Long id) {
        return repairOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu sửa chữa"));
    }

    @Override
    public void addService(Long orderId, Long serviceId, int quantity) {
        return;
    }

    @Override
    public void addPart(Long orderId, Long partId, int quantity) {
        return;
    }

    @Override
    public void finalizeQuotation(Long orderId) {
        return;
    }
    @Override
    public void addItem(Long orderId, Long itemId, ItemType type, int quantity) {

        RepairOrder order = repairOrderRepository.findById(orderId).orElseThrow();

        if (type == ItemType.SERVICE) {

            ServiceClass service = serviceRepository.findById(itemId).orElseThrow();

            Optional<WorkOrderItem> existing =
                    workOrderItemRepository.findByRepairOrderAndService(order, service);

            if (existing.isPresent()) {
                // 👉 đã tồn tại → cộng dồn
                WorkOrderItem item = existing.get();
                item.setQuantity(item.getQuantity() + quantity);
                workOrderItemRepository.save(item);
                return;
            }

            // 👉 chưa có → tạo mới
            WorkOrderItem item = new WorkOrderItem();
            item.setRepairOrder(order);
            item.setService(service);
            item.setQuantity(quantity);
            item.setUnitPrice(service.getPrice());
            item.setType(ItemType.SERVICE);

            workOrderItemRepository.save(item);

        } else if (type == ItemType.PART) {

            Part part = partRepository.findById(itemId).orElseThrow();

            Optional<WorkOrderItem> existing =
                    workOrderItemRepository.findByRepairOrderAndPart(order, part);

            if (existing.isPresent()) {
                // đã tồn tại → cộng dồn
                WorkOrderItem item = existing.get();
                item.setQuantity(item.getQuantity() + quantity);
                workOrderItemRepository.save(item);
                return;
            }

            // 👉 chưa có → tạo mới
            WorkOrderItem item = new WorkOrderItem();
            item.setRepairOrder(order);
            item.setPart(part);
            item.setQuantity(quantity);
            item.setUnitPrice(part.getPrice());
            item.setType(ItemType.PART);

            workOrderItemRepository.save(item);
        }
    }
}
