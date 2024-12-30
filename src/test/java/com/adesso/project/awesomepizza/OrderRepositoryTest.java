package com.adesso.project.awesomepizza;

import com.adesso.project.awesomepizza.entity.Order;
import com.adesso.project.awesomepizza.entity.StatoOrdine;
import com.adesso.project.awesomepizza.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void testCreateOrder() {
        Order order = new Order();
        order.setCustomerName("John Doe");
        order.setStatus(StatoOrdine.IN_CODA);
        order.setOrderCode(UUID.randomUUID().toString());
        order = orderRepository.save(order);

        assertNotNull(order.getId(), "L'ID dell'ordine non dovrebbe essere nullo");
        assertEquals("John Doe", order.getCustomerName(), "Il nome del cliente non corrisponde");
    }

    @Test
    void testFindOrderById() {
        Order order = new Order();
        order.setCustomerName("Jane Doe");
        order.setStatus(StatoOrdine.IN_CODA);
        order.setOrderCode(UUID.randomUUID().toString());
        order = orderRepository.save(order);

        Optional<Order> fetchedOrder = orderRepository.findById(order.getId());
        assertTrue(fetchedOrder.isPresent(), "L'ordine dovrebbe essere presente");
        assertEquals("Jane Doe", fetchedOrder.get().getCustomerName(), "Il nome del cliente non corrisponde");
    }

    @Test
    void testDeleteOrder() {
        Order order = new Order();
        order.setCustomerName("Mark Smith");
        order.setStatus(StatoOrdine.IN_CODA);
        order.setOrderCode(UUID.randomUUID().toString());
        order = orderRepository.save(order);

        orderRepository.deleteById(order.getId());
        Optional<Order> deletedOrder = orderRepository.findById(order.getId());
        assertFalse(deletedOrder.isPresent(), "L'ordine non dovrebbe essere presente dopo la cancellazione");
    }
}