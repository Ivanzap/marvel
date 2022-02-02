TRUNCATE SCHEMA PUBLIC AND COMMIT;
ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;

INSERT INTO CHARACTERS(name, description)
VALUES ('SPIDER-MAN', 'WEB'),
       ('VENOM', 'BLACK MESA'),
       ('BAD', 'BAD DICR'),
       ('VAGNUM', 'PIST'),
       ('TERROR', 'MILL'),
       ('PACKMAN', 'GAME'),
       ('DEADPOOL', 'FILM'),
       ('WINDOW', 'MICROSOFT'),
       ('PALAC', 'HZ'),
       ('NEMO', 'KAPITAN');

INSERT INTO COMICS(title, description)
VALUES ('Spider-Man 1', 'Comic about Spider-Man'),
       ('Comic 2', 'Comic about many people'),
       ('Iron Man', 'Comic about Iron Man'),
       ('Chuck Norris', 'Comic about super man'),
       ('Jan Clod Vandam', 'Comic about second super man'),
       ('Spider-Man 2', 'Comic part 2');