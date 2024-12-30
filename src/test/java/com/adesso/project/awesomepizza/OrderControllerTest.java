package com.adesso.project.awesomepizza;

import com.adesso.project.awesomepizza.controller.OrderController;
import com.adesso.project.awesomepizza.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /*@Test
    void testCreateOrder() {
        Order order = new Order(1L, "Pizza Margherita", 1, 10.0);
        when(orderService.createOrder(order)).thenReturn(order);

        ResponseEntity<Order> response = orderController.createOrder(order);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(orderService, times(1)).createOrder(order);
    }

    @Test
    void testFindOrderById() {
        Order order = new Order(1L, "Pizza Margherita", 1, 10.0);
        when(orderService.getOrderById(1L)).thenReturn(order);

        ResponseEntity<Order> response = orderController.findOrderById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Pizza Margherita", response.getBody().getName());
        verify(orderService, times(1)).getOrderById(1L);
    }*/
}