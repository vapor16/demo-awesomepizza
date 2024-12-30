package com.adesso.project.awesomepizza.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {
    private String customerName;
    private List<OrderPizzaRequestDTO> pizzas;
}
