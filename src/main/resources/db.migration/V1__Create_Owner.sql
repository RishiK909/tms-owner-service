CREATE TABLE owners (
        id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
        user_id         UUID NOT NULL,
        first_name      VARCHAR(50) NOT NULL,
        last_name       VARCHAR(50) NOT NULL,
        phone_number    VARCHAR(15) NOT NULL,
        address         VARCHAR(255),
        city            VARCHAR(50),
        state           VARCHAR(50),
        country         VARCHAR(50),
        postal_code     VARCHAR(10),

        created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        created_by      BIGINT,
        updated_at      TIMESTAMP,
        updated_by      BIGINT,
        status          VARCHAR(20) NOT NULL DEFAULT 'Active',
        deleted_at      TIMESTAMP,
        version         BIGINT NOT NULL DEFAULT 0,

        CONSTRAINT uq_owners_user_id UNIQUE (user_id),
        CONSTRAINT uq_owners_phone_number UNIQUE (phone_number)
);

CREATE INDEX idx_owners_user_id ON owners(user_id);
CREATE INDEX idx_owners_status ON owners(status);