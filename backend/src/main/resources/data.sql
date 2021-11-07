DROP TABLE IF EXISTS wine;

CREATE TABLE wine (
    id INT AUTO_INCREMENT PRIMARY KEY,
    lot_code VARCHAR,
    volume DOUBLE,
    description VARCHAR,
    tank_code VARCHAR,
    product_state VARCHAR,
    owner_name VARCHAR
);

DROP TABLE IF EXISTS component;

CREATE TABLE component (
    id INT AUTO_INCREMENT PRIMARY KEY,
    wine_lot_code VARCHAR,
    percentage DOUBLE,
    year INT,
    variety VARCHAR,
    region VARCHAR
);
