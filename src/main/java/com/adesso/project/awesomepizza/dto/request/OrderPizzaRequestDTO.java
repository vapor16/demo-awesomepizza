package com.adesso.project.awesomepizza.dto.request;

import lombok.Data;

@Data
public class OrderPizzaRequestDTO {
    private String name;
    private Integer quantity;
}