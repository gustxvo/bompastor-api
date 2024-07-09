INSERT INTO tb_users (user_id, name, email, password, role) VALUES ('00a7b810-9dad-11d1-80b4-00c04fd430c8', 'Gustavo Braga', 'braga@bp.com', '$2a$10$.KId/voVjkx.pqJ61YA9QOjWkE3qdPr8aN0k9ZJ.pv23f2Z2O0bNu', 'ADMIN');
INSERT INTO tb_users (user_id, name, email, password, role) VALUES ('01a7b810-9dad-11d1-80b4-00c04fd430c8', 'Matheus Torres', 'matheus@bp.com', '$2a$10$lRBjDa0XzSlFb32kcs54Y.lTraiyS5Bal2Tz8riZiI6J/1pwWaTvy', 'LEADER');
INSERT INTO tb_users (user_id, name, email, password, role) VALUES ('02a7b810-9dad-11d1-80b4-00c04fd430c8', 'Gustavo Almeida', 'almeida@bp.com', '$2a$10$6kP1upOqNT7Lxu3X6/2BeurvwM3k.FrOjnJwOWLFzxbMTEaWYfYtO', 'WORKER');

INSERT INTO tb_sectors (leader_id, name) VALUES ('01a7b810-9dad-11d1-80b4-00c04fd430c8', 'Recep');
INSERT INTO tb_sectors (leader_id, name) VALUES ('01a7b810-9dad-11d1-80b4-00c04fd430c8', 'Mídia');
INSERT INTO tb_sectors (leader_id, name) VALUES ('00a7b810-9dad-11d1-80b4-00c04fd430c8', 'Louvor');

INSERT INTO tb_sectors_users (sector_id, user_id) VALUES (1, '00a7b810-9dad-11d1-80b4-00c04fd430c8');
INSERT INTO tb_sectors_users (sector_id, user_id) VALUES (1, '02a7b810-9dad-11d1-80b4-00c04fd430c8');
INSERT INTO tb_sectors_users (sector_id, user_id) VALUES (2, '02a7b810-9dad-11d1-80b4-00c04fd430c8');

INSERT INTO tb_events (sector_id, date_time) VALUES (1, '2024-07-30 20:00');
INSERT INTO tb_events (sector_id, date_time) VALUES (2, '2024-07-20 20:00');
INSERT INTO tb_events (sector_id, date_time) VALUES (3, '2024-07-10 20:00');
INSERT INTO tb_events (sector_id, date_time) VALUES (1, '2024-07-28 20:00');

INSERT INTO tb_events_workers (event_id, worker_id) VALUES (1, '00a7b810-9dad-11d1-80b4-00c04fd430c8');
INSERT INTO tb_events_workers (event_id, worker_id) VALUES (2, '00a7b810-9dad-11d1-80b4-00c04fd430c8');
INSERT INTO tb_events_workers (event_id, worker_id) VALUES (2, '02a7b810-9dad-11d1-80b4-00c04fd430c8');