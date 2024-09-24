INSERT INTO books (isbn, title, genre, description, author)
VALUES ('978-3-16-148410-0', 'The Great Gatsby', 'Fiction', 'A novel about the American dream and tragic love story.',
        'F. Scott Fitzgerald')
ON CONFLICT DO NOTHING;

INSERT INTO books (isbn, title, genre, description, author)
VALUES ('978-0-452-28423-4', 'To Kill a Mockingbird', 'Fiction',
        'A novel centered around racial injustice in the American South.', 'Harper Lee')
ON CONFLICT DO NOTHING;

INSERT INTO books (isbn, title, genre, description, author)
VALUES ('978-0-7432-7356-5', '1984', 'Dystopian', 'A dystopian novel depicting a totalitarian regime and surveillance.',
        'George Orwell')
ON CONFLICT DO NOTHING;

INSERT INTO books (isbn, title, genre, description, author)
VALUES ('978-0-14-017739-8', 'The Catcher in the Rye', 'Fiction',
        'A story about adolescent alienation and loss of innocence.', 'J.D. Salinger')
ON CONFLICT DO NOTHING;

INSERT INTO books (isbn, title, genre, description, author)
VALUES ('978-1-4767-4658-6', 'The Road', 'Post-apocalyptic',
        'A father and son journey through a bleak, post-apocalyptic landscape.', 'Cormac McCarthy')
ON CONFLICT DO NOTHING;

INSERT INTO books (isbn, title, genre, description, author)
VALUES ('978-0-14-311158-0', 'Life of Pi', 'Adventure', 'A young boy stranded on a lifeboat with a Bengal tiger.',
        'Yann Martel')
ON CONFLICT DO NOTHING;

INSERT INTO books (isbn, title, genre, description, author)
VALUES ('978-0-06-112008-4', 'Brave New World', 'Dystopian',
        'A futuristic society where emotions and individuality are suppressed.', 'Aldous Huxley')
ON CONFLICT DO NOTHING;

INSERT INTO books (isbn, title, genre, description, author)
VALUES ('978-0-14-243723-0', 'Moby-Dick', 'Adventure',
        'A tale of obsession, revenge, and the hunt for the white whale.', 'Herman Melville')
ON CONFLICT DO NOTHING;

INSERT INTO books (isbn, title, genre, description, author)
VALUES ('978-0-15-602732-8', 'The Old Man and the Sea', 'Fiction', 'A short novel about an old fisherman',
        'Ernest Hemingway')
ON CONFLICT (isbn) DO NOTHING;
