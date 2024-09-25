CREATE TABLE IF NOT EXISTS loans (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    issued_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    due_to TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now() + INTERVAL '30 days',
    returned_at TIMESTAMP WITH TIME ZONE
);

CREATE INDEX IF NOT EXISTS idx_loans_user_id ON loans (user_id);
CREATE INDEX IF NOT EXISTS idx_loans_book_id ON loans (book_id);