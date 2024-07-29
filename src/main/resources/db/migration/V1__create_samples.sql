DROP TABLE IF EXISTS permissions;
CREATE TABLE IF NOT EXISTS permissions (
    id SERIAL,
    permission_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS roles;
CREATE TABLE IF NOT EXISTS roles (
    id SERIAL,
    role_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS roles_permissions;
CREATE TABLE IF NOT EXISTS roles_permissions (
    role_id BIGINT UNSIGNED NOT NULL,
    permission_id BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES roles(id),
    FOREIGN KEY (permission_id) REFERENCES permissions(id)
);

DROP TABLE IF EXISTS user_groups;
CREATE TABLE IF NOT EXISTS user_groups (
    id SERIAL,
    display_id VARCHAR(32) UNIQUE NOT NULL,
    role_id BIGINT UNSIGNED NOT NULL UNIQUE,
    user_group_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users (
    id SERIAL,
    display_id VARCHAR(32) UNIQUE NOT NULL,
    user_group_id BIGINT UNSIGNED NOT NULL UNIQUE,
    login_id VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    user_name VARCHAR(50) NOT NULL,
    mail_address VARCHAR(50) UNIQUE NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_group_id) REFERENCES user_groups(id)
);

DROP TABLE IF EXISTS samples;
CREATE TABLE IF NOT EXISTS samples (
    id SERIAL,
    display_id VARCHAR(32) UNIQUE NOT NULL,
    text1 VARCHAR(50),
    num1 int,
    PRIMARY KEY (id)
);