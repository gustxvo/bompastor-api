CREATE TABLE tb_notification_token (
    device_id SERIAL PRIMARY KEY,
    token TEXT UNIQUE NOT NULL,
    user_id UUID REFERENCES tb_user ON DELETE CASCADE
);
