CREATE SCHEMA IF NOT EXISTS users;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS authority;

CREATE TABLE IF NOT EXISTS authority (
    id serial NOT NULL PRIMARY KEY,
    role varchar(32) NOT NULL UNIQUE,
    date timestamp WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS users (
    id serial NOT NULL PRIMARY KEY,
    name varchar(32) NOT NULL,
    age int NOT NULL,
    lastname varchar(32) NOT NULL,
    username varchar(32) NOT NULL UNIQUE,
    password varchar(256) NOT NULL,
    date timestamp WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    role text[] NOT NULL 
    -- authority_id int NOT NULL,
    -- FOREIGN KEY(authority_id) REFERENCES authority(id)
);

INSERT INTO authority (role) VALUES ('ROLE_SAVE');
INSERT INTO authority (role) VALUES ('ROLE_UPDATE');
INSERT INTO authority (role) VALUES ('ROLE_DELETE');
INSERT INTO authority (role) VALUES ('ROLE_READ');