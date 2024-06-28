CREATE TABLE _user (
                       id SERIAL PRIMARY KEY,
                       first_name VARCHAR(255) NOT NULL,
                       last_name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(255) NOT NULL,
                       CONSTRAINT chk_role CHECK (role IN ('USER', 'ADMIN'))
);

CREATE TABLE routine (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         start_date_time TIMESTAMP NOT NULL,
                         renewal_interval_seconds INT NOT NULL,
                         renewal_interval_minutes INT NOT NULL,
                         renewal_interval_hours INT NOT NULL,
                         renewal_interval_days INT NOT NULL,
                         renewal_interval_months INT NOT NULL,
                         renewal_interval_years INT NOT NULL,
                         user_id INT NOT NULL,
                         CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES _user(id) ON DELETE CASCADE
);

CREATE TABLE routine_execution (
                                   id BIGSERIAL PRIMARY KEY,
                                   routine_id BIGINT,
                                   execution_time TIMESTAMP,
                                   CONSTRAINT fk_routine FOREIGN KEY (routine_id) REFERENCES routine(id) ON DELETE CASCADE
);
