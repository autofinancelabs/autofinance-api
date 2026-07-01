-- ---------------------------------------------------------------------------
-- Capitalization: enum name (varchar) -> number of days (integer).
-- Generalizes the capitalization frequency of a nominal rate to any day count
-- (1=daily, 30=monthly, 90=quarterly, 180=semiannual, 360=annual, or custom).
-- Existing rows carry the enum name and are mapped to their day equivalents.
-- ---------------------------------------------------------------------------

ALTER TABLE credit_simulations
    ALTER COLUMN rate_capitalization TYPE integer USING (
        CASE rate_capitalization
            WHEN 'DAILY'      THEN 1
            WHEN 'MONTHLY'    THEN 30
            WHEN 'QUARTERLY'  THEN 90
            WHEN 'SEMIANNUAL' THEN 180
            WHEN 'ANNUAL'     THEN 360
            ELSE NULL
        END
    );

ALTER TABLE credit_simulations
    ALTER COLUMN cost_of_capital_capitalization TYPE integer USING (
        CASE cost_of_capital_capitalization
            WHEN 'DAILY'      THEN 1
            WHEN 'MONTHLY'    THEN 30
            WHEN 'QUARTERLY'  THEN 90
            WHEN 'SEMIANNUAL' THEN 180
            WHEN 'ANNUAL'     THEN 360
            ELSE NULL
        END
    );
