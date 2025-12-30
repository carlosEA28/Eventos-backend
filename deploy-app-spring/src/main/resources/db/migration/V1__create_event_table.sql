CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE events(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description  VARCHAR(100) NOT NULL,
    img_url  VARCHAR(100) NOT NULL,
    event_url  VARCHAR(100) NOT NULL,
    remote BOOLEAN NOT NULL DEFAULT FALSE,
    date TIMESTAMP NOT NULL
);