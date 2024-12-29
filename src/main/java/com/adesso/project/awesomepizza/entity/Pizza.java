package com.adesso.project.awesomepizza.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "pizzas")
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}