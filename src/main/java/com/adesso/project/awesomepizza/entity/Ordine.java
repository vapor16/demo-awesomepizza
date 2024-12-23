package com.adesso.project.awesomepizza.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ordine")
public class Ordine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cliente;

    @Column(unique = true, nullable = false)
    private String ordineCode; // Codice univoco generato casualmente

    @Enumerated(EnumType.STRING)
    private StatoOrdine stato;

    private LocalDateTime dataOraCreazione;

    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdinePizza> ordinePizze;

    @PrePersist
    private void generateOrdineCode() {
        if (ordineCode == null || ordineCode.isEmpty()) {
            ordineCode = generateRandomCode();
        }
    }

    private String generateRandomCode() {
        Random random = new Random();
        String letter1 = random.nextInt(26) + "A";
        String letter2 = random.nextInt(26) + "A";
        int number = random.nextInt(900) + 100; // Numero a 3 cifre
        return "".concat(letter1).concat(letter2).concat(String.valueOf(number));
    }
}
