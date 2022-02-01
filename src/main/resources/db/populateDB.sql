TRUNCATE SCHEMA PUBLIC AND COMMIT;
ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;

INSERT INTO CHARACTERS(name, description)
VALUES ('SPIDER-MAN', 'WEB'),
       ('VENOM', 'BLACK MESA');

INSERT INTO COMICS(title, description)
VALUES ('Spider-Man 1', 'Comic about Spider-Man'),
       ('Spider-Man 2', 'Comic part 2');