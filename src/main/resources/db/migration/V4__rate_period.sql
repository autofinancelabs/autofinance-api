-- ---------------------------------------------------------------------------
-- Rate period: the period (in days) the rate value is quoted over, orthogonal to
-- the capitalization frequency. Applies to both rate types; NULL = annual.
--   * NOMINAL: capitalization stays the compounding frequency; rate_period is the
--     (new) quoting period of the nominal rate (NULL = annual, TNA).
--   * EFFECTIVE: the quoting period previously lived in capitalization; it now
--     moves to rate_period, and capitalization becomes NULL.
-- ---------------------------------------------------------------------------

ALTER TABLE credit_simulations
    ADD COLUMN rate_period                 integer,
    ADD COLUMN cost_of_capital_rate_period integer;

-- Move the effective rate's quoting period from capitalization to rate_period.
UPDATE credit_simulations
    SET rate_period = rate_capitalization,
        rate_capitalization = NULL
    WHERE rate_type = 'EFFECTIVE';

UPDATE credit_simulations
    SET cost_of_capital_rate_period = cost_of_capital_capitalization,
        cost_of_capital_capitalization = NULL
    WHERE cost_of_capital_type = 'EFFECTIVE';
