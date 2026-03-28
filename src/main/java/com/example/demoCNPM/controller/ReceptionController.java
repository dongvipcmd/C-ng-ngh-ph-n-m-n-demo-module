package com.example.demoCNPM.controller;

import com.example.demoCNPM.entity.Car;
import com.example.demoCNPM.entity.Client;
import com.example.demoCNPM.entity.RepairOrder;
import com.example.demoCNPM.entity.WorkOrderItem;
import com.example.demoCNPM.enums.ItemType;
import com.example.demoCNPM.enums.RepairStatus;
import com.example.demoCNPM.service.interfaces.CarService;
import com.example.demoCNPM.service.interfaces.ClientService;
import com.example.demoCNPM.service.interfaces.RepairOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/reception")
@RequiredArgsConstructor
public class ReceptionController {

    private final ClientService clientService;
    private final CarService carService;
    private final RepairOrderService repairOrderService;

    @GetMapping("/home")
    public String home(){
        return "reception/home";
    }

    //Hiển thị form tìm khách
    @GetMapping("/search-client")
    public String searchClient(@RequestParam(required = false) String keyword,
                               Model model) {

        if (keyword != null && !keyword.trim().isEmpty()) {

            var clients = clientService.search(keyword);

            if (clients.isEmpty()) {
                model.addAttribute("error", "Không tìm thấy khách hàng!");
            } else {
                model.addAttribute("clients", clients);
            }
        }

        model.addAttribute("keyword", keyword);

        return "reception/search-client";
    }

    //xu ly tim kiem khach
    @PostMapping("/search-client")
    public String searchClientPost(@RequestParam String keyword,
                                   RedirectAttributes redirectAttributes) {

        if (keyword == null || keyword.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error",
                    "Vui lòng nhập tên hoặc SĐT!");
            return "redirect:/reception/search-client";
        }

        return "redirect:/reception/search-client?keyword=" + keyword;
    }

    // 3. Chọn khách → hiển thị xe
    @GetMapping("/select-client/{id}")
    public String selectClient(@PathVariable Long id, Model model) {
        model.addAttribute("cars", carService.getByClientId(id));
        model.addAttribute("clientId", id);
        return "reception/select-car";
    }

    // Form thêm xe
    @GetMapping("/add-car/{clientId}")
    public String showAddCarForm(@PathVariable Long clientId, Model model) {
        model.addAttribute("car", new Car());
        model.addAttribute("clientId", clientId);
        return "reception/add-car";
    }

    //  Lưu xe mới
    @PostMapping("/add-car")
    public String addCar(@ModelAttribute Car car, @RequestParam Long clientId) {
        Client client = new Client();
        client.setId(clientId);
        car.setClient(client);
        carService.save(car);
        return "redirect:/reception/select-client/" + clientId;
    }

    @GetMapping("/orders/create/{carId}")
    public String createOrderForm(@PathVariable Long carId, Model model) {
        RepairOrder order = new RepairOrder();
        order.setCar(carService.findById(carId));
        model.addAttribute("order", order);
        return "reception/create-order";
    }

    // ================== LƯU PHIẾU ==================
    @PostMapping("/orders/create")
    public String createOrder(@ModelAttribute RepairOrder order) {
        Long carId = order.getCar().getId();
        order.setCar(carService.findById(carId));
        order.setStatus(RepairStatus.PENDING);
        order.setReceivedDate(java.time.LocalDateTime.now());
        repairOrderService.save(order);
        return "redirect:/reception/orders";
    }

    @GetMapping("/orders")
    public String listOrders(Model model) {
        model.addAttribute("orders", repairOrderService.findAll());
        return "reception/list-orders";
    }

    @GetMapping("/orders/orders-detail/{id}")
    public String detailOrder(@PathVariable Long id, Model model) {
        RepairOrder repairOrder = repairOrderService.findById(id);
        model.addAttribute("order", repairOrder);

        List<WorkOrderItem> serviceItems = repairOrder.getItems().stream()
                .filter(i -> i.getType() == ItemType.SERVICE)
                .toList();

        List<WorkOrderItem> partItems = repairOrder.getItems().stream()
                .filter(i -> i.getType() == ItemType.PART)
                .toList();

        model.addAttribute("serviceItems", serviceItems);
        model.addAttribute("partItems", partItems);

        double total = 0;
        if(serviceItems != null) {
            total += serviceItems.stream().mapToDouble(i -> i.getUnitPrice() * i.getQuantity()).sum();
        }
        if(partItems != null) {
            total += partItems.stream().mapToDouble(i -> i.getUnitPrice() * i.getQuantity()).sum();
        }
        model.addAttribute("totalAmount", total);

        return "reception/order-detail";
    }
}