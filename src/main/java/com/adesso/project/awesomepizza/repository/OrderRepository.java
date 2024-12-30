package com.adesso.project.awesomepizza.repository;

import com.adesso.project.awesomepizza.entity.Order;
import com.adesso.project.awesomepizza.entity.StatoOrdine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderCode(String orderCode);

    Boolean existsByStatus(StatoOrdine status);
}