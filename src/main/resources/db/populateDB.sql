TRUNCATE SCHEMA PUBLIC AND COMMIT;
ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;

INSERT INTO CHARACTERS(name, description)
VALUES ('SPIDER-MAN', 'WEB'),
       ('VENOM', 'BLACK MESA'),
       ('BAD MAN', 'BAD DICR'),
       ('VAGNUM', 'PIST'),
       ('TERROR', 'MILL'),
       ('PACKMAN', 'GAME'),
       ('DEADPOOL', 'FILM Film'),
       ('WINDOW', 'Microsoft'),
       ('NEW SPIDER-MAN', 'NEW WEB'),
       ('NEMO', 'Capitan');

INSERT INTO COMICS(title, description)
VALUES ('Spider-Man 1', 'Comic about Spider-Man'),
       ('Comic 1', 'Comic 1'),
       ('Blade', 'Comic about Blade'),
       ('X-MEN', 'Comic about X_MEN'),
       ('VENOM VS X-MEN', 'Comic about VENOM and X-MEN'),
       ('Spider-Man 2', 'Comic 2');

INSERT INTO CHARACTERS_COMICS (CHARACTER_ID, COMIC_ID)
VALUES (100000, 100010),
       (100000, 100015),
       (100001, 100015),
       (100001, 100014),
       (100001, 100011),
       (100008, 100014);