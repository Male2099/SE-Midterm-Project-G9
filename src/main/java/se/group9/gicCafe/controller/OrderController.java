package se.group9.gicCafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import se.group9.gicCafe.service.OrderService;

@RequestMapping("/admin")
@Controller
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        super();
        this.orderService = orderService;
    }

    @GetMapping("/orderHistory")
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());

        return "admin/orderHistory";
    }
}
