package com.adesso.project.awesomepizza.controller;

import com.adesso.project.awesomepizza.dto.request.OrderRequestDTO;
import com.adesso.project.awesomepizza.dto.response.OrderResponseDTO;
import com.adesso.project.awesomepizza.dto.response.PizzaResponseDTO;
import com.adesso.project.awesomepizza.entity.Order;
import com.adesso.project.awesomepizza.entity.StatoOrdine;
import com.adesso.project.awesomepizza.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public OrderResponseDTO createOrder(@RequestBody OrderRequestDTO request) {
        Order order = orderService.createOrder(request.getCustomerName(), request.getPizzas());
        return mapToResponseDTO(order);
    }

    @GetMapping
    public List<OrderResponseDTO> getOrderQueue() {
        return orderService.getOrderQueue().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{orderCode}")
    public OrderResponseDTO getOrderStatus(@PathVariable String orderCode) {
        Order order = orderService.getOrderStatus(orderCode);
        return mapToResponseDTO(order);
    }

    @PatchMapping("/{orderCode}")
    public OrderResponseDTO updateOrderStatus(@PathVariable String orderCode, @RequestParam StatoOrdine status) {
        Order order = orderService.updateOrderStatus(orderCode, status);
        return mapToResponseDTO(order);
    }

    private OrderResponseDTO mapToResponseDTO(Order order) {
        OrderResponseDTO response = new OrderResponseDTO();
        response.setOrderCode(order.getOrderCode());
        response.setStatus(order.getStatus().getStatus());
        response.setCustomerName(order.getCustomerName());
        response.setPizzas(order.getPizzas().stream()
                .map(orderPizza -> {
                    PizzaResponseDTO pizzaResponse = new PizzaResponseDTO();
                    pizzaResponse.setName(orderPizza.getPizza().getName());
                    pizzaResponse.setQuantity(orderPizza.getQuantity());
                    return pizzaResponse;
                })
                .collect(Collectors.toList()));
        return response;
    }
}