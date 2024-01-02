DROP TABLE AUTH_USER IF EXISTS;

CREATE TABLE AUTH_USER
(
    id        INTEGER IDENTITY PRIMARY KEY,
    username  VARCHAR(50),
    password  VARCHAR(100),
    role    VARCHAR(100),
    telephone VARCHAR(20),
    email     VARCHAR(100),
    location VARCHAR(255)
);
CREATE UNIQUE INDEX user_name_idx ON AUTH_USER (username);

DROP TABLE AUTH_token IF EXISTS;
CREATE TABLE AUTH_token
(
    id        INTEGER IDENTITY PRIMARY KEY,
    expired     boolean,
    revoked  boolean,
    user_id varchar(100),
    token_str    LONGVARCHAR ,
    token_type VARCHAR(20),
);

