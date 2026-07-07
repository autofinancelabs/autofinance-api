-- ---------------------------------------------------------------------------
-- Drop the vehicle-offer "plan" (name + installments): dead metadata that no
-- calculation consumed. The loan term is decided per credit simulation, not on
-- the offer, so the plan is removed from the Vehicle Offers aggregate.
-- ---------------------------------------------------------------------------

ALTER TABLE vehicle_offers DROP COLUMN plan_name;
ALTER TABLE vehicle_offers DROP COLUMN plan_installments;
