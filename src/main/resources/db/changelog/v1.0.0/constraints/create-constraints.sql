ALTER TABLE avatar
    ADD CONSTRAINT "pk.avatar.user_id" PRIMARY KEY (user_id);

ALTER TABLE avatar
    ADD CONSTRAINT "fk.avatar.user_id"
    FOREIGN KEY (user_id) REFERENCES "user"(id);