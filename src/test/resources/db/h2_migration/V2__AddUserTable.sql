
CREATE TABLE account
(
    id int NOT NULL AUTO_INCREMENT,
    email varchar(50) NOT NULL,
    first_name varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
    user_role varchar(50) NOT NULL,
    password varchar(250) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (email)
);

ALTER TABLE property
    ADD account_id int;

ALTER TABLE property
    ADD CONSTRAINT fk_account
        FOREIGN KEY (account_id) REFERENCES account(id);