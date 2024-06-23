DROP TABLE IF EXISTS samples;
CREATE TABLE IF NOT EXISTS samples (
    id SERIAL,
    display_id VARCHAR(32) UNIQUE NOT NULL,
    text1 VARCHAR(50),
    num1 int,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users (
    id SERIAL,
    display_id VARCHAR(32) UNIQUE NOT NULL,
    login_id VARCHAR(50) UNIQUE,
    password VARCHAR(100),
    user_name VARCHAR(50),
    mail_address VARCHAR(50) UNIQUE,
    PRIMARY KEY (id)
);