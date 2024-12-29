package com.adesso.project.awesomepizza.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class OrderResponseDTO {
    private String orderCode;
    private String status;
    private String customerName;
    private List<PizzaResponseDTO> pizzas;
}