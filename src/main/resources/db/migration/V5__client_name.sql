-- ---------------------------------------------------------------------------
-- Add the client's personal name (given names + family names). Nullable so
-- existing rows (registered before names existed) keep loading; new clients are
-- required to provide them at the API/validation layer.
-- ---------------------------------------------------------------------------

ALTER TABLE clients ADD COLUMN first_name varchar(120);
ALTER TABLE clients ADD COLUMN last_name  varchar(120);
