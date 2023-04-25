CREATE TABLE app_user (
    uuid UUID NOT NULL PRIMARY KEY,
    username VARCHAR(32) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE role (
    id SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(10) NOT NULL
);

CREATE TABLE user_role (
    user_id UUID NOT NULL REFERENCES app_user(uuid),
    role_id INTEGER NOT NULL REFERENCES role(id),
    CONSTRAINT user_role_pkey PRIMARY KEY(user_id, role_id)
);
