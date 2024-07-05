INSERT INTO tb_users (user_id, name, email, password, role) VALUES ('00a7b810-9dad-11d1-80b4-00c04fd430c8', 'Gustavo Braga', 'braga@bp.com', 'braga123', 'ADMIN');
INSERT INTO tb_users (user_id, name, email, password, role) VALUES ('01a7b810-9dad-11d1-80b4-00c04fd430c8', 'Matheus Torres', 'matheus@bp.com', 'matheus123', 'LEADER');
INSERT INTO tb_users (user_id, name, email, password, role) VALUES ('02a7b810-9dad-11d1-80b4-00c04fd430c8', 'Gustavo Almeida', 'almeida@bp.com', 'almeida123', 'WORKER');

INSERT INTO tb_sectors (sector_id, leader_id, name) VALUES (1, '01a7b810-9dad-11d1-80b4-00c04fd430c8', 'Recep');
INSERT INTO tb_sectors (sector_id, leader_id, name) VALUES (2, '01a7b810-9dad-11d1-80b4-00c04fd430c8', 'Midia');
INSERT INTO tb_sectors (sector_id, leader_id, name) VALUES (3, '00a7b810-9dad-11d1-80b4-00c04fd430c8', 'Louvor');

INSERT INTO tb_sectors_users (sector_id, user_id) VALUES (1, '00a7b810-9dad-11d1-80b4-00c04fd430c8');
INSERT INTO tb_sectors_users (sector_id, user_id) VALUES (1, '02a7b810-9dad-11d1-80b4-00c04fd430c8');
INSERT INTO tb_sectors_users (sector_id, user_id) VALUES (2, '02a7b810-9dad-11d1-80b4-00c04fd430c8');

--INSERT INTO tb_events (event_id, sector_id, date_time) VALUES (1, 1, '2024-07-30 20:00');
--INSERT INTO tb_events (event_id, sector_id, date_time) VALUES (2, 2, '2024-07-20 20:00');
--INSERT INTO tb_events (event_id, sector_id, date_time) VALUES (3, 3, '2024-07-10 20:00');
--INSERT INTO tb_events (event_id, sector_id, date_time) VALUES (4, 1, '2024-07-28 20:00');
--
--INSERT INTO tb_events_workers (event_id, worker_id) VALUES (1, '00a7b810-9dad-11d1-80b4-00c04fd430c8');
--INSERT INTO tb_events_workers (event_id, worker_id) VALUES (2, '00a7b810-9dad-11d1-80b4-00c04fd430c8');
--INSERT INTO tb_events_workers (event_id, worker_id) VALUES (2, '02a7b810-9dad-11d1-80b4-00c04fd430c8');