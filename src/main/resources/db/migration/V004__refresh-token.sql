CREATE TABLE tb_refresh_token (
    refresh_token_id SERIAL PRIMARY KEY,
    token TEXT UNIQUE NOT NULL,
    expiration_date TIMESTAMP NOT NULL,
    user_id UUID REFERENCES tb_user ON DELETE CASCADE
);
