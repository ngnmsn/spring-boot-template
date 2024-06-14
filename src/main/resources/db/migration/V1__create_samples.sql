CREATE TABLE IF NOT EXISTS samples (
    id SERIAL,
    text1 VARCHAR(50),
    num1 int,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXIST users (
    id SERIAL,
    loginId VARCHAR(50),
    password VARCHAR(50),
    userName VARCHAR(50),
    mailAddress VARCHAR(50),
    PRIMARY KEY (id)
)