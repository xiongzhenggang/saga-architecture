DROP TABLE Customer IF EXISTS;

CREATE TABLE CUSTOMER
(
    id        INTEGER IDENTITY PRIMARY KEY,
    name  VARCHAR(200),
    rejection_reason  VARCHAR(100),
    version    bigint,
    amount     DECIMAL
);

-- create table customer_credit_reservations
-- (
--     credit_reservations_key bigint not null,
--     customer_id bigint not null,
--     amount DECIMAL,
--     primary key (credit_reservations_key, customer_id)
-- );
-- CREATE TABLE ACCOUNT_CREDIT
-- (
--     id        INTEGER IDENTITY PRIMARY KEY,
--     amount     DECIMAL
-- );

CREATE TABLE credit_reservation (
        id BIGINT IDENTITY PRIMARY KEY,
        customer_id BIGINT,
        order_id BIGINT,
        reservation DECIMAL
);