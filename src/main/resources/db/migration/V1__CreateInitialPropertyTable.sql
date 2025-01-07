CREATE TABLE address
(
    id          int         NOT NULL AUTO_INCREMENT,
    street      varchar(50) NOT NULL,
    city        varchar(50) NOT NULL,
    postal_Code varchar(15) NOT NULL,
    country     varchar(50) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (street)
);
CREATE TABLE property
(
    id            int          NOT NULL AUTO_INCREMENT,
    description   varchar(250) NOT NULL,
    price         decimal      NOT NULL,
    property_type varchar(50)  NOT NULL,
    listing_type  varchar(50)  NOT NULL,
    address_id    int,
    PRIMARY KEY (id),
    FOREIGN KEY (address_id) REFERENCES address (id)
);
CREATE TABLE property_surface_area
(
    id                   int         NOT NULL AUTO_INCREMENT,
    area_in_square_metre double NOT NULL,
    name_of_surface_area varchar(50) NOT NULL,
    property_id          int,
    PRIMARY KEY (id),
    FOREIGN KEY (property_id) REFERENCES property (id)
);