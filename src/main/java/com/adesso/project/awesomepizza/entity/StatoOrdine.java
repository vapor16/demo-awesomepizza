package com.adesso.project.awesomepizza.entity;

public enum StatoOrdine {
    IN_CODA, IN_PREPARAZIONE, COMPLETATO;

    // Setter (anche se gli enum in genere non hanno setter, possiamo aggiungere uno per esempio se necessario)
    public static StatoOrdine fromString(String status) {
        return switch (status) {
            case "IN_CODA" -> IN_CODA;
            case "IN_PREPARAZIONE" -> IN_PREPARAZIONE;
            case "COMPLETATO" -> COMPLETATO;
            default -> throw new IllegalArgumentException("Unknown status: " + status);
        };
    }

    // Getter
    public String getStatus() {
        return name();
    }
}
