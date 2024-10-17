
CREATE TABLE account
(
    id int NOT NULL AUTO_INCREMENT,
    email nvarchar(50) NOT NULL,
    first_name nvarchar(50) NOT NULL,
    last_name nvarchar(50) NOT NULL,
    user_role nvarchar(50) NOT NULL,
    password nvarchar(250) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (email)
);

ALTER TABLE property
    ADD account_id int;

ALTER TABLE property
    ADD CONSTRAINT fk_account
        FOREIGN KEY (account_id) REFERENCES account(id);