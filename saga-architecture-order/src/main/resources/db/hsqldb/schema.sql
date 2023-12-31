-- CREATE SCHEMA IF NOT EXISTS eventuate AUTHORIZATION SA;
DROP TABLE ORDERS IF EXISTS;
CREATE TABLE ORDERS
(
    id        INTEGER IDENTITY PRIMARY KEY,
    state  VARCHAR(50),
    rejection_reason  VARCHAR(100),
    version    bigint,
    user_id bigint,
    amount     DECIMAL
);
DROP TABLE SAGA_INSTANCE IF EXISTS;
CREATE TABLE  SAGA_INSTANCE(
        id VARCHAR(255)  NOT NULL,
        saga_type VARCHAR(255) NOT NULL,
        state_name VARCHAR(255) NOT NULL,
        last_request_id VARCHAR(100),
        end_state TINYINT,
        compensating TINYINT,
        failed TINYINT,
        saga_data_type VARCHAR(255) NOT NULL,
        saga_data_json VARCHAR(255) NOT NULL,
        PRIMARY KEY (id)
);
DROP TABLE destination_resource IF EXISTS;
CREATE TABLE  destination_resource(
        id bigint NOT NULL,
        saga_id VARCHAR(255) NOT NULL,
        destination  VARCHAR(255) ,
        resource VARCHAR(255),
        PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS saga_instance_participants (
    saga_type VARCHAR(100) NOT NULL,
    saga_id VARCHAR(100) NOT NULL,
    destination VARCHAR(100) NOT NULL,
    resource VARCHAR(100) NOT NULL,
    PRIMARY KEY(saga_type, saga_id, destination, resource)
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

create table IF NOT EXISTS saga_lock_table(
        target VARCHAR(100) PRIMARY KEY,
        saga_type VARCHAR(100) NOT NULL,
        saga_Id VARCHAR(100) NOT NULL
);

create table IF NOT EXISTS saga_stash_table(
     message_id VARCHAR(100) PRIMARY KEY,
     target VARCHAR(100) NOT NULL,
     saga_type VARCHAR(100) NOT NULL,
     saga_id VARCHAR(100) NOT NULL,
     message_headers VARCHAR(255) NOT NULL,
     message_payload VARCHAR(255) NOT NULL
);
