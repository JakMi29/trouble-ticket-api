CREATE TABLE trouble_ticket (
    id VARCHAR(255) PRIMARY KEY CHECK (LENGTH(id) >= 1),
    external_id VARCHAR(255) NOT NULL UNIQUE CHECK (LENGTH(external_id) >= 1),
    service_id BIGINT NOT NULL CHECK (service_id >= 1),
    description TEXT NOT NULL CHECK (LENGTH(description) >= 1),
    status VARCHAR(50) NOT NULL CHECK (status IN ('NEW', 'ACKNOWLEDGED', 'IN_PROGRESS', 'RESOLVED', 'CLOSED', 'REJECTED'))
);