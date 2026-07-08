-- ---------------------------------------------------------------------------
-- Expand the vehicle offer's 3D model: more silhouettes, a free hex body/window
-- color (instead of the 3-value enum), and cosmetic options (sport wheels,
-- spoiler, panoramic roof, license plate). All new columns are nullable so rows
-- without a 3D model (and rows created before an option existed) keep loading;
-- a null option reads as "off".
-- ---------------------------------------------------------------------------

-- Preset: allow the four new silhouettes.
ALTER TABLE vehicle_offers DROP CONSTRAINT ck_offer_3d_preset;
ALTER TABLE vehicle_offers ADD CONSTRAINT ck_offer_3d_preset
    CHECK (model_3d_preset IS NULL OR model_3d_preset IN
        ('SEDAN', 'SUV', 'PICKUP', 'HATCHBACK', 'VAN', 'COUPE', 'MOTORCYCLE'));

-- Color: enum -> free hex. Drop the CHECK, rename, widen, and convert existing values.
ALTER TABLE vehicle_offers DROP CONSTRAINT ck_offer_3d_color;
ALTER TABLE vehicle_offers RENAME COLUMN model_3d_color TO model_3d_body_color;
ALTER TABLE vehicle_offers ALTER COLUMN model_3d_body_color TYPE varchar(9);
UPDATE vehicle_offers SET model_3d_body_color = CASE model_3d_body_color
    WHEN 'TEAL'    THEN '#16b1b1'
    WHEN 'AMBER'   THEN '#f0a021'
    WHEN 'CRIMSON' THEN '#d93a54'
    ELSE model_3d_body_color
END;

-- New cosmetic columns.
ALTER TABLE vehicle_offers ADD COLUMN model_3d_window_color varchar(9);
ALTER TABLE vehicle_offers ADD COLUMN model_3d_sport_wheels boolean;
ALTER TABLE vehicle_offers ADD COLUMN model_3d_spoiler      boolean;
ALTER TABLE vehicle_offers ADD COLUMN model_3d_pano_roof    boolean;
ALTER TABLE vehicle_offers ADD COLUMN model_3d_plate        varchar(12);
