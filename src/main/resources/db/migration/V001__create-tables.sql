CREATE TABLE tb_user (
    user_id UUID PRIMARY KEY,
    email TEXT UNIQUE NOT NULL,
    name TEXT NOT NULL,
    password TEXT NOT NULL,
    role SMALLINT NOT NULL
);

CREATE UNIQUE INDEX email_idx ON tb_user (email);

CREATE TABLE tb_sector (
    sector_id SERIAL PRIMARY KEY,
    leader_id UUID REFERENCES tb_user ON DELETE SET NULL,
    name TEXT NOT NULL
);

CREATE TABLE tb_event (
    event_id SERIAL PRIMARY KEY,
    sector_id INTEGER REFERENCES tb_sector ON DELETE RESTRICT,
    date_time TIMESTAMP(6)
);

CREATE TABLE tb_event_worker (
    event_id BIGINT NOT NULL REFERENCES tb_event ON DELETE CASCADE,
    worker_id UUID NOT NULL REFERENCES tb_user ON DELETE CASCADE,
    PRIMARY KEY (event_id, worker_id)
);

CREATE TABLE tb_sector_worker (
    sector_id INTEGER NOT NULL REFERENCES tb_sector ON DELETE CASCADE,
    worker_id UUID NOT NULL REFERENCES tb_user ON DELETE CASCADE,
    PRIMARY KEY (sector_id, worker_id)
);
