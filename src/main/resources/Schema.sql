
CREATE TABLE student
(
    id         INTEGER NOT NULL,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    email      VARCHAR(255),
    CONSTRAINT pk_student PRIMARY KEY (id)
);