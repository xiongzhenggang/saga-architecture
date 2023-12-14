DROP TABLE Customer IF EXISTS;

CREATE TABLE Customer
(
    id        INTEGER IDENTITY PRIMARY KEY,
    name  VARCHAR(200),
    rejection_reason  VARCHAR(100),
    version    bigint,
    customer_id bigint,
    amount     DECIMAL
);

-- CREATE TABLE ACCOUNT_CREDIT
-- (
--     id        INTEGER IDENTITY PRIMARY KEY,
--     amount     DECIMAL
-- );

CREATE TABLE credit_reservation (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        customer_id BIGINT,
        order_id BIGINT,
        reservation DECIMAL
);