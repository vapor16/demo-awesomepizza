package com.adesso.project.awesomepizza.entity;

public enum StatoOrdine {
    IN_CODA, IN_PREPARAZIONE, COMPLETATO;

    // Getter
    public String getStatus() {
        return name();
    }

    // Setter (anche se gli enum in genere non hanno setter, possiamo aggiungere uno per esempio se necessario)
    public static StatoOrdine fromString(String status) {
        switch (status) {
            case "IN_CODA":
                return IN_CODA;
            case "IN_PREPARAZIONE":
                return IN_PREPARAZIONE;
            case "COMPLETATO":
                return COMPLETATO;
            default:
                throw new IllegalArgumentException("Unknown status: " + status);
        }
    }
}
