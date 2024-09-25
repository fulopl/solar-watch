INSERT INTO role (id, name)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN');

INSERT INTO user_entity (id, username, password)
VALUES (1, 'admin', '$2a$10$22ZndzPnmpZSeazR7ZvgW.h/FIqrentk8.1SW59ojJCnaQ6MnZlDa'),
       (2, 'user0', '$2a$10$ZhJUwpHtUDZi87r6VQedvup2S3ciz89tpuf8KPwphEwxiL/qTrhKa'),
       (3, 'user1', '$2a$10$5hZWTtO8dYqMxy/3fSoKIOWpp0gQpAAJZ2x4270Cx8r3wsZWqMpd.'),
       (4, 'user2', '$2a$10$/tmBd1CQUEparWurBsRcnuZKronXtF2liXTIUxEq7zBBWUbfm2Ag.'),
       (5, 'user3', '$2a$10$4j9URPgRHAML5m5lTt/vr.oBm/5iQnLnMhzCWiYCvMEB/awlO.gQ2'),
       (6, 'user4', '$2a$10$Nl2lEs8XBC8u08.mq.nime2zI3NaezuUlPR.oPFY3XihTjVYqok2O');

INSERT INTO user_entity_role (user_entity_id, role_id)
VALUES (1,1),
       (1,2),
       (2,1),
       (3,1),
       (4,1),
       (4,2),
       (5,1),
       (6,1);