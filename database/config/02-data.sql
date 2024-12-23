-- Inserimento degli ordini
INSERT INTO ordine (cliente, ordine_code, stato, data_ora_creazione) VALUES ('Mario Rossi', 'AB123', 'IN_CODA', NOW());
INSERT INTO ordine (cliente, ordine_code, stato, data_ora_creazione) VALUES ('Luca Bianchi', 'CD456', 'IN_CODA', NOW());
INSERT INTO ordine (cliente, ordine_code, stato, data_ora_creazione) VALUES ('Giulia Verdi', 'EF789', 'IN_CODA', NOW());

-- Inserimento delle pizze
INSERT INTO pizza (nome) VALUES ('Margherita');
INSERT INTO pizza (nome) VALUES ('Diavola');
INSERT INTO pizza (nome) VALUES ('Quattro Formaggi');
INSERT INTO pizza (nome) VALUES ('Capricciosa');
INSERT INTO pizza (nome) VALUES ('Bufala');

-- Collega pizze agli ordini con quantità
-- Ordine 1
INSERT INTO ordine_pizza (ordine_id, pizza_id, quantity) VALUES (1, 1, 2); -- Margherita x2
INSERT INTO ordine_pizza (ordine_id, pizza_id, quantity) VALUES (1, 2, 1); -- Diavola x1

-- Ordine 2
INSERT INTO ordine_pizza (ordine_id, pizza_id, quantity) VALUES (2, 3, 1); -- Quattro Formaggi x1
INSERT INTO ordine_pizza (ordine_id, pizza_id, quantity) VALUES (2, 4, 3); -- Capricciosa x3

-- Ordine 3
INSERT INTO ordine_pizza (ordine_id, pizza_id, quantity) VALUES (3, 5, 3); -- Bufala x3
