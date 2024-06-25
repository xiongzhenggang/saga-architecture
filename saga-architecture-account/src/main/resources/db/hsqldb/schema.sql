DROP TABLE ACCOUNT_USER IF EXISTS;
CREATE TABLE ACCOUNT_USER
(
    ID        INTEGER IDENTITY PRIMARY KEY,
    USER_ID BIGINT,
    USER_NAME  VARCHAR(200),
    VERSION    BIGINT,
    CREATE_TIME TIMESTAMP,
    UPDATE_TIME TIMESTAMP,
    CREATED_BY BIGINT,
    UPDATED_BY BIGINT,
    AMOUNT     DECIMAL
);

DROP TABLE IF EXISTS CREDIT_RESERVATION;
CREATE TABLE CREDIT_RESERVATION (
        ID BIGINT IDENTITY PRIMARY KEY,
        CUSTOMER_ID BIGINT,
        ORDER_ID BIGINT,
        RESERVATION DECIMAL
);

DROP TABLE IF EXISTS SAGA_MESSAGE;
CREATE TABLE SAGA_MESSAGE(
                             ID INTEGER NOT NULL  , --租户号
                             SERIAL VARCHAR(255) NOT NULL  , --消息事件流水号：同一消息幂等
                             SAGA_ID VARCHAR(255) NOT NULL  ,
                             PAYLOAD VARCHAR(900)   , --事件内容
                             TYPE VARCHAR(32)   , --事件消息类型
                             SEND_STATUS VARCHAR(32)   , --事件发送状态，发送中，已完成
                             SOURCE VARCHAR(32)   , --事件来源
                             HEADERS VARCHAR(255)   , --消息头
                             UPDATED_TIME NUMERIC   , --更新时间
                             UPDATED_BY VARCHAR(32)   , --更新人
                             CREATED_TIME NUMERIC   , --创建时间
                             CREATED_BY VARCHAR(32)   , --创建人
                             PRIMARY KEY (ID)
)  ; --消息事件记录