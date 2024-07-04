INSERT INTO tb_users VALUES ('00a7b810-9dad-11d1-80b4-00c04fd430c8', 'Braga', 'braga@bp.com', 'braga123', 'ADMIN');
INSERT INTO tb_users VALUES ('01a7b810-9dad-11d1-80b4-00c04fd430c8', 'Matheus', 'matheus@bp.com', 'matheus123', 'LEADER');
INSERT INTO tb_users VALUES ('02a7b810-9dad-11d1-80b4-00c04fd430c8', 'Almeida', 'almeida@bp.com', 'almeida123', 'WORKER');

INSERT INTO tb_sectors VALUES (1, 'Recep');
INSERT INTO tb_sectors VALUES (2, 'Midia');

INSERT INTO tb_sectors_users VALUES (1, '00a7b810-9dad-11d1-80b4-00c04fd430c8');
INSERT INTO tb_sectors_users VALUES (1, '01a7b810-9dad-11d1-80b4-00c04fd430c8');
INSERT INTO tb_sectors_users VALUES (2, '02a7b810-9dad-11d1-80b4-00c04fd430c8');