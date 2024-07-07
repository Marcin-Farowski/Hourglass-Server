CREATE TABLE _user
(
    id            BIGSERIAL PRIMARY KEY,
    first_name    TEXT NOT NULL,
    last_name     TEXT NOT NULL,
    date_of_birth DATE NOT NULL,
    gender        TEXT NOT NULL,
    email         TEXT NOT NULL UNIQUE,
    password      TEXT NOT NULL,
    role          TEXT NOT NULL,
    CONSTRAINT chk_gender CHECK ( gender IN ('MALE', 'FEMALE', 'NOT_SPECIFIED') ),
    CONSTRAINT chk_role CHECK (role IN ('USER', 'ADMIN'))
);

CREATE TABLE routine
(
    id              BIGSERIAL PRIMARY KEY,
    name            TEXT      NOT NULL,
    start_date_time TIMESTAMP NOT NULL,
    seconds         INT       NOT NULL,
    minutes         INT       NOT NULL,
    hours           INT       NOT NULL,
    days            INT       NOT NULL,
    months          INT       NOT NULL,
    years           INT       NOT NULL,
    user_id         BIGINT    NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES _user (id) ON DELETE CASCADE
);

CREATE TABLE routine_execution
(
    id             BIGSERIAL PRIMARY KEY,
    routine_id     BIGINT,
    execution_time TIMESTAMP,
    CONSTRAINT fk_routine FOREIGN KEY (routine_id) REFERENCES routine (id) ON DELETE CASCADE
);
