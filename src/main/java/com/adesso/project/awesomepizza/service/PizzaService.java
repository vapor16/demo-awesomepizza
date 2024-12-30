package com.adesso.project.awesomepizza.service;

import com.adesso.project.awesomepizza.dto.request.PizzaRequestDTO;
import com.adesso.project.awesomepizza.dto.response.PizzaResponseDTO;
import com.adesso.project.awesomepizza.entity.Pizza;
import com.adesso.project.awesomepizza.exception.PizzaValidationException;
import com.adesso.project.awesomepizza.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PizzaService implements GeneralService {

    @Autowired
    private PizzaRepository pizzaRepository;

    public PizzaResponseDTO createPizza(PizzaRequestDTO pizzaRequestDTO) {
        if (pizzaRepository.findByName(pizzaRequestDTO.getName()).isPresent()) {
            throw new PizzaValidationException("Pizza: '" + pizzaRequestDTO.getName() + "' è già esistente.");
        }

        Pizza pizza = new Pizza();
        pizza.setName(pizzaRequestDTO.getName());
        Pizza savedPizza = pizzaRepository.save(pizza);
        return mapToResponseDTO(savedPizza);
    }

    public List<PizzaResponseDTO> getAllPizzas() {
        return pizzaRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<PizzaResponseDTO> getPizzaById(Long id) {
        return pizzaRepository.findById(id)
                .map(this::mapToResponseDTO);
    }

    public Optional<PizzaResponseDTO> getPizzaByName(String name) {
        return pizzaRepository.findByName(name)
                .map(this::mapToResponseDTO);
    }

    public PizzaResponseDTO updatePizza(Long id, PizzaRequestDTO pizzaRequestDTO) {
        Pizza existingPizza = pizzaRepository.findById(id)
                .orElseThrow(() -> new PizzaValidationException("Pizza con id " + id + " non trovato."));
        existingPizza.setName(pizzaRequestDTO.getName());
        Pizza updatedPizza = pizzaRepository.save(existingPizza);
        return mapToResponseDTO(updatedPizza);
    }

    public void deletePizza(Long id) {
        Pizza existingPizza = pizzaRepository.findById(id)
                .orElseThrow(() -> new PizzaValidationException("Pizza con id " + id + " non trovato."));
        pizzaRepository.delete(existingPizza);
    }

    private PizzaResponseDTO mapToResponseDTO(Pizza pizza) {
        PizzaResponseDTO responseDTO = new PizzaResponseDTO();
        responseDTO.setId(pizza.getId());
        responseDTO.setName(pizza.getName());
        return responseDTO;
    }
}

