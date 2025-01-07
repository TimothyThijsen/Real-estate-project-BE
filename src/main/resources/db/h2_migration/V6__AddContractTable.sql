ALTER TABLE transaction
    ADD column description varchar(250);

CREATE TABLE contract
(
    id                        int     NOT NULL AUTO_INCREMENT,
    property_id               int     NOT NULL,
    customer_id               int     NOT NULL,
    is_active                 BOOLEAN NOT NULL,
    minimum_contract_end_date DATE    NOT NULL,
    start_date                DATE    NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (property_id) REFERENCES property (id),
    FOREIGN KEY (customer_id) REFERENCES account (id)
);
