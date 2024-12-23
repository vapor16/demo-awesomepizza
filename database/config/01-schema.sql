CREATE TABLE ordine (
    id BIGSERIAL PRIMARY KEY,
    cliente VARCHAR(100) NOT NULL,
    ordine_code VARCHAR(10) UNIQUE NOT NULL,
    stato VARCHAR(255),
    data_ora_creazione TIMESTAMP
);

CREATE TABLE pizza (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

CREATE TABLE ordine_pizza (
    id BIGSERIAL PRIMARY KEY, -- ID univoco casuale generato
    ordine_id BIGINT NOT NULL,  -- Chiave esterna verso ordine
    pizza_id BIGINT NOT NULL,   -- Chiave esterna verso pizza
    quantity INT NOT NULL,      -- Quantità di questa pizza in questo ordine
    FOREIGN KEY (ordine_id) REFERENCES ordine(id) ON DELETE CASCADE,
    FOREIGN KEY (pizza_id) REFERENCES pizza(id) ON DELETE CASCADE
);
