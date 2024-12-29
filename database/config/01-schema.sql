-- Tabella per le pizze
CREATE TABLE pizzas (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- Tabella per gli ordini
CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    order_code VARCHAR(255) NOT NULL UNIQUE,
    status VARCHAR(50) NOT NULL,
    customer_name VARCHAR(255) NOT NULL
);

-- Tabella di associazione tra ordini e pizze con quantità
CREATE TABLE order_pizzas (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    pizza_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    CONSTRAINT fk_pizza FOREIGN KEY (pizza_id) REFERENCES pizzas(id) ON DELETE CASCADE
);
