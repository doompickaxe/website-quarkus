ALTER TABLE city
    ADD COLUMN uuid uuid NOT NULL DEFAULT uuidv7();

ALTER TABLE company
    ADD COLUMN uuid uuid NOT NULL DEFAULT uuidv7();
