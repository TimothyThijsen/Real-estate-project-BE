ALTER TABLE property
    ADD COLUMN listing_status varchar(50);
ALTER TABLE property ALTER COLUMN description varchar(500) NOT NULL;

CREATE TABLE request
(
    id                   int         NOT NULL AUTO_INCREMENT,
    property_id          int,
    account_id           int,
    status       varchar(50) NOT NULL,
    date         date        NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (property_id) REFERENCES property (id),
    FOREIGN KEY (account_id) REFERENCES account (id)
);
