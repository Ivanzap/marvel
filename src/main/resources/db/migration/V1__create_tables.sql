DROP TABLE IF EXISTS CHARACTERS_COMICS;
DROP TABLE IF EXISTS COMICS;
DROP TABLE IF EXISTS CHARACTERS;

CREATE TABLE CHARACTERS
(
    id serial PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255)
);

CREATE TABLE COMICS
(
    id serial PRIMARY KEY,
    title VARCHAR(255),
    description VARCHAR(255)
);

CREATE TABLE CHARACTERS_COMICS
(
    character_id INTEGER,
    comic_id INTEGER,
    FOREIGN KEY (character_id) REFERENCES CHARACTERS (id),
    FOREIGN KEY (comic_id) REFERENCES COMICS (id)
);

INSERT INTO CHARACTERS(name, description)
VALUES ('SPIDER-MAN', 'WEB'),               -- 1
       ('VENOM', 'BLACK MESA'),             -- 2
       ('BAD MAN', 'BAD DICR'),             -- 3
       ('VAGNUM', 'PIST'),                  -- 4
       ('TERROR', 'MILL'),                  -- 5
       ('PACKMAN', 'GAME'),                 -- 6
       ('DEADPOOL', 'FILM Film'),           -- 7
       ('WINDOW', 'Microsoft'),             -- 8
       ('NEW SPIDER-MAN', 'NEW WEB'),       -- 9
       ('NEW SPIDER-MAN 2', 'NEW STRONG'),  -- 10
       ('NEW SPIDER-MAN 3', 'NEW SPEED'),   -- 11
       ('DR STRANGE', 'NEW SUPER HERO'),    -- 12
       ('BLADE', 'VAMPIRE HERO'),           -- 13
       ('NEMO', 'Capitan');                 -- 14

INSERT INTO COMICS(title, description)
VALUES ('Spider-Man 1', 'Comic about Spider-Man'),          -- 1
       ('Comic 1', 'Comic 1'),                              -- 2
       ('Blade', 'Comic about Blade'),                      -- 3
       ('X-MEN', 'Comic about X_MEN'),                      -- 4
       ('VENOM VS X-MEN', 'Comic about VENOM and X-MEN'),   -- 5
       ('Comic 2', 'Comic number 2'),                       -- 6
       ('Blade 2', 'Comic about Blade 2'),                  -- 7
       ('Blade 3', 'Comic about Blade 3'),                  -- 8
       ('Dr Strange', 'Comic about Strange'),               -- 9
       ('Dr Strange 2', 'Comic about Strange 2'),           -- 10
       ('Spider-Man 2', 'Comic 2');                         -- 11

INSERT INTO CHARACTERS_COMICS (CHARACTER_ID, COMIC_ID)
VALUES (1, 1),
       (1, 5),
       (2, 6),
       (2, 5),
       (2, 2),
       (5, 5),
       (7, 5),
       (10, 11),
       (11, 10),
       (12, 9),
       (12, 10),
       (13, 3),
       (13, 7),
       (13, 8);
