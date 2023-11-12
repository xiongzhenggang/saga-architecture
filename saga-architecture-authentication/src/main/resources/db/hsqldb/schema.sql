DROP TABLE account IF EXISTS;

CREATE TABLE account
(
    id        INTEGER IDENTITY PRIMARY KEY,
    username  VARCHAR(50),
    password  VARCHAR(100),
    avatar    VARCHAR(100),
    telephone VARCHAR(20),
    email     VARCHAR(100)
);
CREATE UNIQUE INDEX account_user ON account (username);
CREATE UNIQUE INDEX account_telephone ON account (telephone);
CREATE UNIQUE INDEX account_email ON account (email);

DROP TABLE token IF EXISTS;

CREATE TABLE token
(
    id        INTEGER IDENTITY PRIMARY KEY,
    expired     boolean,
    revoked  boolean,
    user_id varchar(100),
    token_str    TEXT ,
    tokenType VARCHAR(20),
);
