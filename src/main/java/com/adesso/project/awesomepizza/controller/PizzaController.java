package com.adesso.project.awesomepizza.controller;

import com.adesso.project.awesomepizza.dto.request.PizzaRequestDTO;
import com.adesso.project.awesomepizza.dto.response.PizzaResponseDTO;
import com.adesso.project.awesomepizza.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pizzas")
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;

    // Create a new pizza
    @PostMapping
    public ResponseEntity<PizzaResponseDTO> createPizza(@RequestBody PizzaRequestDTO pizzaRequestDTO) {
        PizzaResponseDTO createdPizza = pizzaService.createPizza(pizzaRequestDTO);
        return ResponseEntity.ok(createdPizza);
    }

    // Get all pizzas
    @GetMapping
    public ResponseEntity<List<PizzaResponseDTO>> getAllPizzas() {
        List<PizzaResponseDTO> pizzas = pizzaService.getAllPizzas();
        return ResponseEntity.ok(pizzas);
    }

    // Get pizza by ID
    @GetMapping("/{id}")
    public ResponseEntity<PizzaResponseDTO> getPizzaById(@PathVariable Long id) {
        return pizzaService.getPizzaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get pizza by name
    @GetMapping("/search")
    public ResponseEntity<PizzaResponseDTO> getPizzaByName(@RequestParam String name) {
        return pizzaService.getPizzaByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update pizza
    @PutMapping("/{id}")
    public ResponseEntity<PizzaResponseDTO> updatePizza(@PathVariable Long id, @RequestBody PizzaRequestDTO pizzaRequestDTO) {
        PizzaResponseDTO updatedPizza = pizzaService.updatePizza(id, pizzaRequestDTO);
        return ResponseEntity.ok(updatedPizza);
    }

    // Delete pizza
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePizza(@PathVariable Long id) {
        pizzaService.deletePizza(id);
        return ResponseEntity.noContent().build();
    }
}
