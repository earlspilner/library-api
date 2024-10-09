DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users
(
    id          INTEGER IDENTITY PRIMARY KEY,
    full_name   VARCHAR(255)                        NOT NULL,
    username    VARCHAR(100)                        NOT NULL,
    email       VARCHAR(100)                        NOT NULL,
    password    VARCHAR(255)                        NOT NULL,
    created_utc TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_utc TIMESTAMP                           NULL
);

CREATE INDEX IF NOT EXISTS idx_users_username ON users (username);

CREATE TABLE IF NOT EXISTS user_roles
(
    user_id   INTEGER     NOT NULL,
    user_role VARCHAR(50) NOT NULL,
    CONSTRAINT fk_user_roles_user_id FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT pk_user_roles PRIMARY KEY (user_id, user_role)
);