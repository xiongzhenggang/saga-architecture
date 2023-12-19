DROP TABLE USER IF EXISTS;

CREATE TABLE USER
(
    id        INTEGER IDENTITY PRIMARY KEY,
    username  VARCHAR(50),
    password  VARCHAR(100),
    role    VARCHAR(100),
    telephone VARCHAR(20),
    email     VARCHAR(100),
    location VARCHAR(255)
);
CREATE UNIQUE INDEX user_name_idx ON USER (username);

DROP TABLE token IF EXISTS;
CREATE TABLE token
(
    id        INTEGER IDENTITY PRIMARY KEY,
    expired     boolean,
    revoked  boolean,
    user_id varchar(100),
    token_str    LONGVARCHAR ,
    token_type VARCHAR(20),
);
