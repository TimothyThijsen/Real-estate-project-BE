CREATE TABLE refresh_token (
    id int PRIMARY KEY AUTO_INCREMENT,
    token VARCHAR(255) NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    account_id INT NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account(id)
);