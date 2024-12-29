-- Inserisci alcune pizze nella tabella `pizzas`
INSERT INTO pizzas (name) VALUES
('Margherita'),
('Diavola'),
('Quattro Formaggi'),
('Capricciosa'),
('Vegetariana');

-- Inserisci alcuni ordini nella tabella `orders`
INSERT INTO orders (order_code, status, customer_name) VALUES
('ORD001', 'IN_CODA', 'John Doe'),
('ORD002', 'IN_PREPARAZIONE', 'Jane Smith'),
('ORD003', 'COMPLETATO', 'Alice Brown');

-- Inserisci associazioni nella tabella `order_pizzas`
INSERT INTO order_pizzas (order_id, pizza_id, quantity) VALUES
(1, 1, 2), -- 2 Margherite per il primo ordine
(1, 2, 1), -- 1 Diavola per il primo ordine
(2, 3, 1), -- 1 Quattro Formaggi per il secondo ordine
(2, 4, 2), -- 2 Capricciose per il secondo ordine
(3, 5, 3); -- 3 Vegetariane per il terzo ordine
