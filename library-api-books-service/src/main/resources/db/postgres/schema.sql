CREATE TABLE IF NOT EXISTS books (
    id SERIAL PRIMARY KEY,
    isbn VARCHAR(20) NOT NULL UNIQUE,
    title VARCHAR(255) NOT NULL,
    genre VARCHAR(255) NOT NULL,
    description VARCHAR(2000) NOT NULL,
    author VARCHAR(255) NOT NULL,
    appeared_utc TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_books_isbn ON books (isbn);

ALTER SEQUENCE books_id_seq RESTART WITH 100;