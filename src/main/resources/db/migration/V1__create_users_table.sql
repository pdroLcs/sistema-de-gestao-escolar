CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    public_id UUID NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role VARCHAR(50) NOT NULL,

    CONSTRAINT fk_user_roles_user
                        FOREIGN KEY (user_id)
                        REFERENCES users(id)
                        ON DELETE CASCADE
);