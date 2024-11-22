ALTER TABLE account
    DROP COLUMN user_role;

CREATE TABLE user_role
(
    id        int         NOT NULL AUTO_INCREMENT,
    account_id   int         NOT NULL,
    role_name varchar(50) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (account_id) REFERENCES account(id)
);

