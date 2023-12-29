DROP TABLE GOODS IF EXISTS;

CREATE TABLE GOODS
(
    id        INTEGER IDENTITY PRIMARY KEY,
    goods_name  VARCHAR(200),
    total  INTEGER,
    create_time timestamp,
    update_time timestamp
);
