package com.adesso.project.awesomepizza.service;

import com.adesso.project.awesomepizza.dto.request.PizzaRequestDTO;
import com.adesso.project.awesomepizza.entity.Order;
import com.adesso.project.awesomepizza.entity.OrderPizza;
import com.adesso.project.awesomepizza.entity.Pizza;
import com.adesso.project.awesomepizza.entity.StatoOrdine;
import com.adesso.project.awesomepizza.exception.OrderNotFoundException;
import com.adesso.project.awesomepizza.exception.PizzaValidationException;
import com.adesso.project.awesomepizza.repository.OrderRepository;
import com.adesso.project.awesomepizza.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    public Order createOrder(String customerName, List<PizzaRequestDTO> pizzas) {
        if (customerName == null || customerName.isBlank()) {
            throw new PizzaValidationException("Customer name cannot be null or empty");
        }

        if (pizzas == null || pizzas.isEmpty()) {
            throw new PizzaValidationException("At least one pizza must be included in the order");
        }

        Order order = new Order();
        order.setOrderCode(UUID.randomUUID().toString());
        order.setStatus(StatoOrdine.IN_CODA);
        order.setCustomerName(customerName);

        for (PizzaRequestDTO pizzaRequest : pizzas) {
            Pizza pizza = pizzaRepository.findByName(pizzaRequest.getName())
                    .orElseThrow(() -> new PizzaValidationException("Pizza with name " + pizzaRequest.getName() + " not found"));

            if (pizzaRequest.getQuantity() == null || pizzaRequest.getQuantity() <= 0) {
                throw new PizzaValidationException("Invalid quantity for pizza name " + pizzaRequest.getName());
            }

            OrderPizza orderPizza = new OrderPizza();
            orderPizza.setOrder(order);
            orderPizza.setPizza(pizza);
            orderPizza.setQuantity(pizzaRequest.getQuantity());
            order.getPizzas().add(orderPizza);
        }

        return orderRepository.save(order);
    }

    public List<Order> getOrderQueue() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            throw new OrderNotFoundException("No orders found in the queue");
        }
        return orders;
    }

    public Order getOrderStatus(String orderCode) {
        return orderRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new OrderNotFoundException("Order with code " + orderCode + " not found"));

    }

    public Order updateOrderStatus(String orderCode, StatoOrdine status) {
        if (status == null) {
            throw new PizzaValidationException("Status cannot be null");
        }

        Order order = orderRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new OrderNotFoundException("Order with code " + orderCode + " not found"));


        order.setStatus(status);
        return orderRepository.save(order);
    }

}