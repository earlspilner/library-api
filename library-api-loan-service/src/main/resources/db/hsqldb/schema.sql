DROP TABLE IF EXISTS loans;

CREATE TABLE IF NOT EXISTS loans
(
    id          INTEGER IDENTITY PRIMARY KEY,
    user_id     BIGINT                                             NOT NULL,
    book_id     BIGINT                                             NOT NULL,
    issued_at   TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    due_to      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    returned_at TIMESTAMP WITH TIME ZONE
);

CREATE INDEX IF NOT EXISTS idx_loans_user_id ON loans (user_id);
CREATE INDEX IF NOT EXISTS idx_loans_book_id ON loans (book_id);