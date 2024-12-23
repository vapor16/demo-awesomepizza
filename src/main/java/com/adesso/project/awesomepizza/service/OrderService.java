package com.adesso.project.awesomepizza.service;

import com.adesso.project.awesomepizza.entity.Ordine;
import com.adesso.project.awesomepizza.entity.StatoOrdine;
import com.adesso.project.awesomepizza.exception.OrderNotFoundException;
import com.adesso.project.awesomepizza.exception.PizzaValidationException;
import com.adesso.project.awesomepizza.repository.OrdineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService implements GeneralService<Ordine,Long> {

    private OrdineRepository ordineRepository;

    // CREATE
    @Override
    public Ordine create(Ordine ordine) {
        if (ordine.getOrdinePizze() == null || ordine.getOrdinePizze().isEmpty()) {
            throw new PizzaValidationException("Order must contain at least one pizza.");
        }
        ordine.setStato(StatoOrdine.IN_CODA);
        ordine.setDataOraCreazione(LocalDateTime.now());
        return ordineRepository.save(ordine);
    }
    // READ
    @Override
    public Ordine findById(Long id) {
        return ordineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ordine non trovato"));
    }
    @Override
    public List<Ordine> findAll() {
        return ordineRepository.findAll();
    }
    public List<Ordine> findOrderCoda() {
        return ordineRepository.findByStato(StatoOrdine.IN_CODA);
    }

    // UPDATE
    @Override
    public Ordine update(Long id, Ordine ordineAggiornato) {
        Ordine ordine = findById(id);
        ordine.setCliente(ordineAggiornato.getCliente());
        ordine.setOrdinePizze(ordineAggiornato.getOrdinePizze());
        ordine.setStato(ordineAggiornato.getStato());
        return ordineRepository.save(ordine);
    }

    public Ordine updateStato(Long id, StatoOrdine nuovoStato) {
        Ordine ordine = findById(id);
        ordine.setStato(nuovoStato);
        return ordineRepository.save(ordine);
    }

    // DELETE
    @Override
    public void delete(Long id) {
        if (!ordineRepository.existsById(id)) {
            throw new OrderNotFoundException("Ordine N: " + id + " non trovato, impossibile eliminarlo.");
        }
        try {
            ordineRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Errore durante l'eliminazione dell'ordine con ID " + id, e);
        }
    }

    @Autowired
    public void setOrdineRepository(OrdineRepository ordineRepository) {
        this.ordineRepository = ordineRepository;
    }
}