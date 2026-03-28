package com.example.demoCNPM.controller;

import com.example.demoCNPM.entity.Part;
import com.example.demoCNPM.entity.RepairOrder;
import com.example.demoCNPM.entity.ServiceClass;
import com.example.demoCNPM.enums.ItemType;
import com.example.demoCNPM.enums.RepairStatus;
import com.example.demoCNPM.repository.PartRepository;
import com.example.demoCNPM.repository.ServiceRepository;
import com.example.demoCNPM.service.interfaces.RepairOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/technical")
@RequiredArgsConstructor
public class TechnicalController {


    private final RepairOrderService repairOrderService;
    private final ServiceRepository serviceRepository;
    private final PartRepository partRepository;

    @GetMapping("/home")
    public String home(){
        return "technical/home";
    }

    @GetMapping("/{id}/items")
    public String viewPage(@PathVariable Long id, Model model) {

        model.addAttribute("orderId", id);
        model.addAttribute("services", java.util.Collections.emptyList());
        model.addAttribute("parts", java.util.Collections.emptyList());
        model.addAttribute("searched", false);

        return "technical/search-items";
    }

    // Search cả service + part
    @GetMapping("/{id}/search")
    public String search(@PathVariable Long id,
                         @RequestParam(required = false) String keyword,
                         Model model) {

        List<ServiceClass> services = Collections.emptyList();
        List<Part> parts = Collections.emptyList();
        String error = null;

        if (keyword == null || keyword.trim().isEmpty()) {
            error = "Vui lòng nhập tên dịch vụ hoặc phụ tùng!";
        } else {
            services = serviceRepository.findByNameContainingIgnoreCase(keyword);
            parts = partRepository.findByNameContainingIgnoreCase(keyword);

            if (services.isEmpty() && parts.isEmpty()) {
                error = "Không tìm thấy dịch vụ hoặc phụ tùng!";
            }
        }

        model.addAttribute("services", services);
        model.addAttribute("parts", parts);
        model.addAttribute("keyword", keyword);
        model.addAttribute("orderId", id);
        model.addAttribute("error", error);

        return "technical/search-items";
    }

    @PostMapping("/{id}/add-item")
    public String addItem(@PathVariable Long id,
                          @RequestParam Long itemId,
                          @RequestParam ItemType type,
                          @RequestParam int quantity,
                          RedirectAttributes ra) {

        repairOrderService.addItem(id, itemId, type, quantity);

        ra.addFlashAttribute("success", "Đã thêm thành công!");

        return "redirect:/technical/" + id + "/items";
    }

    @PostMapping("/{id}/finalize")
    public String finalizeQuote(@PathVariable Long id, RedirectAttributes ra) {
        RepairOrder order = repairOrderService.findById(id);

        order.setStatus(RepairStatus.QUOTED);
        repairOrderService.save(order);

        ra.addFlashAttribute("success", "Đã xác nhận báo giá!");

        return "redirect:/reception/orders/orders-detail/" + id;
    }
}
