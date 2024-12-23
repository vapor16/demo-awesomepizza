package com.adesso.project.awesomepizza.repository;
import com.adesso.project.awesomepizza.entity.Ordine;
import com.adesso.project.awesomepizza.entity.StatoOrdine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrdineRepository extends JpaRepository<Ordine, Long> {
    List<Ordine> findByStato(StatoOrdine stato);
}
