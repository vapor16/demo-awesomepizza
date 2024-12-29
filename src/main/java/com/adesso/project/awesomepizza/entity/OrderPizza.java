package com.adesso.project.awesomepizza.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "order_pizzas")
public class OrderPizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "pizza_id", nullable = false)
    private Pizza pizza;

    @Column(nullable = false)
    private Integer quantity;
}

