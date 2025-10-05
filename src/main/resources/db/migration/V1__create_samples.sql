DROP TABLE IF EXISTS permissions;
CREATE TABLE IF NOT EXISTS permissions (
    id BIGINT AUTO_INCREMENT,
    permission_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS roles;
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT,
    role_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS roles_permissions;
CREATE TABLE IF NOT EXISTS roles_permissions (
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES roles(id),
    FOREIGN KEY (permission_id) REFERENCES permissions(id)
);

DROP TABLE IF EXISTS user_groups;
CREATE TABLE IF NOT EXISTS user_groups (
    id BIGINT AUTO_INCREMENT,
    display_id VARCHAR(32) UNIQUE NOT NULL,
    role_id BIGINT NOT NULL UNIQUE,
    user_group_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT,
    display_id VARCHAR(32) UNIQUE NOT NULL,
    user_group_id BIGINT NOT NULL UNIQUE,
    login_id VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    user_name VARCHAR(50) NOT NULL,
    mail_address VARCHAR(50) UNIQUE NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_group_id) REFERENCES user_groups(id)
);

DROP TABLE IF EXISTS samples;
CREATE TABLE IF NOT EXISTS samples (
    id BIGINT AUTO_INCREMENT,
    display_id VARCHAR(32) UNIQUE NOT NULL,
    text1 VARCHAR(50),
    num1 int,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);