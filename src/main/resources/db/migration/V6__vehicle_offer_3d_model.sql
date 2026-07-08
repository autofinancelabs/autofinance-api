-- ---------------------------------------------------------------------------
-- Add the optional low-poly 3D model of a vehicle offer: a shape preset and a
-- color. Both nullable so existing rows (and offers created without generating
-- a 3D model) keep loading; enums are persisted as strings and constrained to
-- the domain's allowed values, mirroring ck_offer_currency.
-- ---------------------------------------------------------------------------

ALTER TABLE vehicle_offers ADD COLUMN model_3d_preset varchar(10);
ALTER TABLE vehicle_offers ADD COLUMN model_3d_color  varchar(20);

ALTER TABLE vehicle_offers ADD CONSTRAINT ck_offer_3d_preset
    CHECK (model_3d_preset IS NULL OR model_3d_preset IN ('SEDAN', 'SUV', 'PICKUP'));
ALTER TABLE vehicle_offers ADD CONSTRAINT ck_offer_3d_color
    CHECK (model_3d_color IS NULL OR model_3d_color IN ('TEAL', 'AMBER', 'CRIMSON'));
