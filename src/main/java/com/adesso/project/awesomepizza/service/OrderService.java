package com.adesso.project.awesomepizza.service;

import com.adesso.project.awesomepizza.dto.request.OrderPizzaRequestDTO;
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
public class OrderService implements GeneralService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    public Order createOrder(String customerName, List<OrderPizzaRequestDTO> pizzas) {
        if (customerName == null || customerName.isBlank()) {
            throw new PizzaValidationException("Un ordine deve includere il nome del cliente");
        }
        if (pizzas == null || pizzas.isEmpty()) {
            throw new PizzaValidationException("Un ordine deve avere almeno una pizza");
        }

        Order order = new Order();
        order.setOrderCode(UUID.randomUUID().toString());
        order.setStatus(StatoOrdine.IN_CODA);
        order.setCustomerName(customerName);

        for (OrderPizzaRequestDTO pizzaRequest : pizzas) {
            Pizza pizza = pizzaRepository.findByName(pizzaRequest.getName())
                    .orElseThrow(() -> new PizzaValidationException("Pizza " + pizzaRequest.getName() + " non trovata"));

            if (pizzaRequest.getQuantity() == null || pizzaRequest.getQuantity() <= 0) {
                throw new PizzaValidationException("La quantità non può essere negativa o uguale a zero: " + pizzaRequest.getName());
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
            throw new OrderNotFoundException("Non ci sono ordini");
        }
        return orders;
    }

    public Order getOrderStatus(String orderCode) {
        return orderRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new OrderNotFoundException("Ordine con codice " + orderCode + " non trovato!"));

    }

    public Order updateOrderStatus(String orderCode, StatoOrdine status) {
        if (status == null) {
            throw new PizzaValidationException("L'ordine non può avere uno stato nullo!");
        }

        // Find the order by its order code
        Order order = orderRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new OrderNotFoundException("Ordine con codice " + orderCode + " non trovato!"));

        // Check if the new status is "IN_PREPARAZIONE"
        if (status == StatoOrdine.IN_PREPARAZIONE) {
            // Verify no other orders are in preparation
            var isAnotherOrderInPreparation = orderRepository.existsByStatus(StatoOrdine.IN_PREPARAZIONE);
            if (isAnotherOrderInPreparation) {
                throw new PizzaValidationException("Un altro ordine è già in preparazione, completalo!");
            }
        }

        // Update the order status
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public void deleteOrder(String orderCode) {
        Order order = orderRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new OrderNotFoundException("Ordine non trovato: " + orderCode));
        orderRepository.delete(order);
    }
}