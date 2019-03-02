CREATE TABLE prices(
    id INT NOT NULL AUTO_INCREMENT,
    date DATETIME,
    open DOUBLE,
    low DOUBLE,
    high DOUBLE,
    close DOUBLE,
    avg DOUBLE,
    is_rising BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY date (date)
);