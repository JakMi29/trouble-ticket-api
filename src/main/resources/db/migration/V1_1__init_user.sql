CREATE TABLE _user (
    id SERIAL   NOT NULL,
    username       VARCHAR(255) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    role    VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);