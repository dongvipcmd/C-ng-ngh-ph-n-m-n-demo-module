package com.example.demoCNPM.controller;

import com.example.demoCNPM.entity.RepairOrder;
import com.example.demoCNPM.entity.WorkOrderItem;
import com.example.demoCNPM.enums.ItemType;
import com.example.demoCNPM.service.interfaces.RepairOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class InvoiceController {

    private final RepairOrderService repairOrderService;

    @GetMapping("/reception/{orderId}/print")
    public String printInvoice(@PathVariable Long orderId, Model model) {
        RepairOrder repairOrder = repairOrderService.findById(orderId);
        if (repairOrder == null) {
            throw new RuntimeException("Phiếu sửa chữa không tồn tại!");
        }
        model.addAttribute("order", repairOrder);

        List<WorkOrderItem> serviceItems = repairOrder.getItems().stream()
                .filter(i -> i.getType() == ItemType.SERVICE)
                .toList();

        List<WorkOrderItem> partItems = repairOrder.getItems().stream()
                .filter(i -> i.getType() == ItemType.PART)
                .toList();

        model.addAttribute("serviceItems", serviceItems);
        model.addAttribute("partItems", partItems);

        double totalAmount = 0;
        if (serviceItems != null) {
            totalAmount += serviceItems.stream()
                    .mapToDouble(i -> i.getUnitPrice() * i.getQuantity())
                    .sum();
        }
        if (partItems != null) {
            totalAmount += partItems.stream()
                    .mapToDouble(i -> i.getUnitPrice() * i.getQuantity())
                    .sum();
        }

        model.addAttribute("totalAmount", totalAmount);

        return "reception/invoice";
    }
}