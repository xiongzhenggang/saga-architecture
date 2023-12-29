DROP TABLE saga_instance IF EXISTS;
CREATE TABLE  saga_instance(
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

CREATE TABLE IF NOT EXISTS aggregate_instance_subscriptions(
     aggregate_type VARCHAR(255) DEFAULT NULL,
     aggregate_id VARCHAR(255) NOT NULL,
     event_type VARCHAR(200) NOT NULL,
     saga_id VARCHAR(1000) NOT NULL,
     saga_type VARCHAR(200) NOT NULL,
     PRIMARY KEY(aggregate_id, event_type, saga_id, saga_type)
);

CREATE TABLE IF NOT EXISTS saga_instance_participants (
    saga_type VARCHAR(100) NOT NULL,
    saga_id VARCHAR(100) NOT NULL,
    destination VARCHAR(100) NOT NULL,
    resource VARCHAR(100) NOT NULL,
    PRIMARY KEY(saga_type, saga_id, destination, resource)
);



