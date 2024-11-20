CREATE TABLE transaction
(
    id int NOT NULL AUTO_INCREMENT,
    property_id int NOT NULL,
    customer_id int NOT NULL,
    date DATETIME  NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (property_id) REFERENCES property (id),
    FOREIGN KEY (customer_id) REFERENCES account (id)
);