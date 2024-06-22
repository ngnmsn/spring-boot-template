CREATE TABLE IF NOT EXISTS samples (
    id SERIAL,
    text1 VARCHAR(50),
    num1 int,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users (
    id SERIAL,
    login_id VARCHAR(50) UNIQUE,
    password VARCHAR(100),
    user_name VARCHAR(50),
    mail_address VARCHAR(50) UNIQUE,
    PRIMARY KEY (id)
);