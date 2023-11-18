INSERT INTO USR (usr_id, nm, pwd, nick_nm, use_at) VALUES (1, 'admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin', 1);
INSERT INTO USR (usr_id, nm, pwd, nick_nm, use_at) VALUES (2, 'user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'user', 1);

INSERT INTO AUTH (auth_id) values ('ROLE_USER');
INSERT INTO AUTH (auth_id) values ('ROLE_ADMIN');

INSERT INTO USR_AUTH (usr_id, auth_id) values (1, 'ROLE_USER');
INSERT INTO USR_AUTH (usr_id, auth_id) values (1, 'ROLE_ADMIN');
INSERT INTO USR_AUTH (usr_id, auth_id) values (2, 'ROLE_USER');