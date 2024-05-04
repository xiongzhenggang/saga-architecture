DROP TABLE GOODS_INFO IF EXISTS;

CREATE TABLE GOODS_INFO
(
    ID        INTEGER IDENTITY PRIMARY KEY,
    GOODS_NAME  VARCHAR(200),
    UNIT_PRICE DECIMAL,
    STOCK  INTEGER,
    DESC  VARCHAR(200),
    URL  VARCHAR(200),
    CREATED_BY VARCHAR(255),
    UPDATED_BY VARCHAR(255),
    CREATE_TIME TIMESTAMP,
    UPDATE_TIME TIMESTAMP
);
DROP TABLE IF EXISTS SAGA_MESSAGE;
CREATE TABLE SAGA_MESSAGE(
                             ID INTEGER NOT NULL  , --租户号
                             SERIAL VARCHAR(255) NOT NULL  , --消息事件流水号：同一消息幂等
                             PAYLOAD VARCHAR(900)   , --事件内容
                             TYPE VARCHAR(32)   , --事件消息类型
                             SEND_STATUS VARCHAR(32)   , --事件发送状态，发送中，已完成
                             HEADERS VARCHAR(255)   , --消息头
                             UPDATED_TIME NUMERIC   , --更新时间
                             UPDATED_BY VARCHAR(32)   , --更新人
                             CREATED_TIME NUMERIC   , --创建时间
                             CREATED_BY VARCHAR(32)   , --创建人
                             PRIMARY KEY (ID)