DROP TABLE user_roles IF EXISTS;
DROP TABLE votes IF EXISTS;
DROP TABLE users IF EXISTS;
DROP TABLE items IF EXISTS;
DROP TABLE RESTAURANTS IF EXISTS;

DROP SEQUENCE GLOBAL_SEQ IF EXISTS;
CREATE SEQUENCE GLOBAL_SEQ AS INTEGER START WITH 100000;
DROP SEQUENCE VOTE_SEQ IF EXISTS;
CREATE SEQUENCE VOTE_SEQ AS INTEGER START WITH 100000;
DROP SEQUENCE ITEM_SEQ IF EXISTS;
CREATE SEQUENCE ITEM_SEQ AS INTEGER START WITH 100000;

CREATE TABLE users
(
    id         INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name       VARCHAR(255)            NOT NULL,
    email      VARCHAR(255)            NOT NULL,
    password   VARCHAR(255)            NOT NULL,
    registered TIMESTAMP DEFAULT now() NOT NULL,
    enabled    BOOLEAN   DEFAULT TRUE  NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx
    ON USERS (email);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);

CREATE TABLE restaurants
(
    id   INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE items
(
    id        INTEGER GENERATED BY DEFAULT AS SEQUENCE ITEM_SEQ PRIMARY KEY,
    rest_id   INTEGER      NOT NULL,
    name      VARCHAR(255) NOT NULL,
    price     INTEGER      NOT NULL,
    date_time TIMESTAMP    NOT NULL,
    CONSTRAINT items_idx UNIQUE (rest_id, name, date_time),
    FOREIGN KEY (rest_id) REFERENCES restaurants (id) ON DELETE CASCADE
);

CREATE TABLE votes
(
    id        INTEGER GENERATED BY DEFAULT AS SEQUENCE VOTE_SEQ PRIMARY KEY,
    user_id   INTEGER   NOT NULL,
    rest_id   INTEGER   NOT NULL,
    date_time TIMESTAMP NOT NULL,
--     CONSTRAINT votes_idx UNIQUE (rest_id, user_id, date_time),
    FOREIGN KEY (rest_id) REFERENCES restaurants (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
