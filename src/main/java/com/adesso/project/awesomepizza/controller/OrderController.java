package com.adesso.project.awesomepizza.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.adesso.project.awesomepizza.entity.*;
import com.adesso.project.awesomepizza.service.OrderService;
import com.adesso.project.awesomepizza.exception.InvalidStateException;

@RestController
@RequestMapping("/ordini")
@RequiredArgsConstructor // Utilizza Lombok per il costruttore
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Ordine> creaOrdine(@RequestBody Ordine ordine) {
        return new ResponseEntity<>(orderService.create(ordine), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ordine> trovaOrdine(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Ordine>> trovaTutti() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ordine> aggiornaOrdine(@PathVariable Long id, @RequestBody Ordine ordine) {
        return ResponseEntity.ok(orderService.update(id, ordine));
    }

    @PutMapping("/{id}/stato/{nuovoStato}")
    public ResponseEntity<Ordine> aggiornaStato(@PathVariable Long id, @PathVariable String nuovoStato) {
        StatoOrdine stato;
        try {
            stato = StatoOrdine.valueOf(nuovoStato); // Converte il valore in StatoOrdine
        } catch (IllegalArgumentException e) {
            throw new InvalidStateException("Invalid state: " + nuovoStato);
            // Lancia un'eccezione personalizzata se non valido
        }

        return ResponseEntity.ok(orderService.updateStato(id, stato));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminaOrdine(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
