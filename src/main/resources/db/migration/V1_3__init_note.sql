CREATE TABLE note (
    id VARCHAR(255) PRIMARY KEY,
    text TEXT NOT NULL,
    date TIMESTAMP NOT NULL,
    trouble_ticket_id VARCHAR(255) NOT NULL,
    CONSTRAINT fk_trouble_ticket FOREIGN KEY (trouble_ticket_id) REFERENCES trouble_ticket (id) ON DELETE CASCADE
);
