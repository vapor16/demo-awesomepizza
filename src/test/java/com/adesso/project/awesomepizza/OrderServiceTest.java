package com.adesso.project.awesomepizza;

import com.adesso.project.awesomepizza.dto.request.OrderPizzaRequestDTO;
import com.adesso.project.awesomepizza.entity.Order;
import com.adesso.project.awesomepizza.entity.OrderPizza;
import com.adesso.project.awesomepizza.entity.Pizza;
import com.adesso.project.awesomepizza.exception.PizzaValidationException;
import com.adesso.project.awesomepizza.repository.OrderRepository;
import com.adesso.project.awesomepizza.repository.PizzaRepository;
import com.adesso.project.awesomepizza.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private PizzaRepository pizzaRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrderWithNullCustomerName() {
        List<OrderPizzaRequestDTO> pizzas = new ArrayList<>();
        OrderPizzaRequestDTO pizzaRequest = new OrderPizzaRequestDTO();
        pizzaRequest.setName("Margherita");
        pizzaRequest.setQuantity(2);
        pizzas.add(pizzaRequest);

        PizzaValidationException exception = assertThrows(PizzaValidationException.class, () ->
                orderService.createOrder(null, pizzas));

        assertEquals("Un ordine deve includere il nome del cliente", exception.getMessage());
    }

    @Test
    void testCreateOrderWithEmptyCustomerName() {
        List<OrderPizzaRequestDTO> pizzas = new ArrayList<>();
        OrderPizzaRequestDTO pizzaRequest = new OrderPizzaRequestDTO();
        pizzaRequest.setName("Margherita");
        pizzaRequest.setQuantity(2);
        pizzas.add(pizzaRequest);
        PizzaValidationException exception = assertThrows(PizzaValidationException.class, () ->
                orderService.createOrder("", pizzas));

        assertEquals("Un ordine deve includere il nome del cliente", exception.getMessage());
    }

    @Test
    void testCreateOrderWithNullPizzas() {
        PizzaValidationException exception = assertThrows(PizzaValidationException.class, () ->
                orderService.createOrder("John Doe", null));

        assertEquals("Un ordine deve avere almeno una pizza", exception.getMessage());
    }

    @Test
    void testCreateOrderWithEmptyPizzas() {
        PizzaValidationException exception = assertThrows(PizzaValidationException.class, () ->
                orderService.createOrder("John Doe", new ArrayList<>()));

        assertEquals("Un ordine deve avere almeno una pizza", exception.getMessage());
    }

    @Test
    void testCreateOrderSuccessfully() {
        List<OrderPizzaRequestDTO> pizzas = new ArrayList<>();
        OrderPizzaRequestDTO pizzaRequest = new OrderPizzaRequestDTO();
        pizzaRequest.setName("Margherita");
        pizzaRequest.setQuantity(2);
        pizzas.add(pizzaRequest);

        Pizza pizza = new Pizza();
        pizza.setName("Margherita");
        when(pizzaRepository.findByName("Margherita")).thenReturn(Optional.of(pizza));

        Order savedOrder = new Order();
        savedOrder.setOrderCode(UUID.randomUUID().toString());
        savedOrder.setCustomerName("Anna C");
        var orderPizza = new OrderPizza();
        orderPizza.setPizza(pizza);
        orderPizza.setQuantity(2);
        orderPizza.setOrder(savedOrder);
        savedOrder.setPizzas(List.of(orderPizza));
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        Order result = orderService.createOrder("Anna C", pizzas);

        assertNotNull(result);
        assertEquals("Anna C", result.getCustomerName());
        assertEquals(1, result.getPizzas().size(), "Mismatch in the number of pizzas in the order");
        verify(pizzaRepository, times(1)).findByName("Margherita");
        verify(orderRepository, times(1)).save(any(Order.class));
    }
}