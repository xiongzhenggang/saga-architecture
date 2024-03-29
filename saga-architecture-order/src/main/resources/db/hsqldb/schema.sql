-- CREATE SCHEMA IF NOT EXISTS EVENTUATE AUTHORIZATION SA;
DROP TABLE ORDERS IF EXISTS;
CREATE TABLE ORDERS
(
    ID        INTEGER IDENTITY PRIMARY KEY,
    STATE  VARCHAR(50),
    REJECTION_REASON  VARCHAR(100),
    VERSION    BIGINT,
    USER_ID BIGINT,
    AMOUNT     DECIMAL
);
DROP TABLE SAGA_INSTANCE IF EXISTS;
CREATE TABLE  SAGA_INSTANCE(
        ID VARCHAR(255)  NOT NULL,
        SAGA_TYPE VARCHAR(255) NOT NULL,
        STATE_NAME VARCHAR(255) NOT NULL,
        LAST_REQUEST_ID VARCHAR(100),
        END_STATE TINYINT,
        COMPENSATING TINYINT,
        FAILED TINYINT,
        SAGA_DATA_TYPE VARCHAR(255) NOT NULL,
        SAGA_DATA_JSON VARCHAR(255) NOT NULL,
        PRIMARY KEY (ID)
);
DROP TABLE SAGA_DESTINATION_RESOURCE IF EXISTS;
CREATE TABLE  SAGA_DESTINATION_RESOURCE(
        ID BIGINT NOT NULL,
        SAGA_ID VARCHAR(255) NOT NULL,
        DESTINATION  VARCHAR(255) ,
        RESOURCE VARCHAR(255),
        PRIMARY KEY (ID)
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
)  ; --消息事件记录
-- CREATE TABLE IF NOT EXISTS SAGA_INSTANCE_PARTICIPANTS (
--                                                           SAGA_TYPE VARCHAR(100) NOT NULL,
--                                                           SAGA_ID VARCHAR(100) NOT NULL,
--                                                           DESTINATION VARCHAR(100) NOT NULL,
--                                                           RESOURCE VARCHAR(100) NOT NULL,
--                                                           PRIMARY KEY(SAGA_TYPE, SAGA_ID, DESTINATION, RESOURCE)
-- );
-- CREATE TABLE IF NOT EXISTS SAGA_LOCK_TABLE(
--         TARGET VARCHAR(100) PRIMARY KEY,
--         SAGA_TYPE VARCHAR(100) NOT NULL,
--         SAGA_ID VARCHAR(100) NOT NULL
-- );
--
-- CREATE TABLE IF NOT EXISTS SAGA_STASH_TABLE(
--      MESSAGE_ID VARCHAR(100) PRIMARY KEY,
--      TARGET VARCHAR(100) NOT NULL,
--      SAGA_TYPE VARCHAR(100) NOT NULL,
--      SAGA_ID VARCHAR(100) NOT NULL,
--      MESSAGE_HEADERS VARCHAR(255) NOT NULL,
--      MESSAGE_PAYLOAD VARCHAR(255) NOT NULL
-- );
