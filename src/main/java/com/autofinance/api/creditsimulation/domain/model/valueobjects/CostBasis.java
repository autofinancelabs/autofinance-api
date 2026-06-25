package com.autofinance.api.creditsimulation.domain.model.valueobjects;

/** How a cost amount is computed each time it applies. */
public enum CostBasis {
    /** A fixed amount (the value is the amount). */
    FIXED,
    /** A rate applied to the outstanding balance (e.g. credit-life insurance / desgravamen). */
    ON_BALANCE,
    /** A rate applied to the vehicle sale price (e.g. all-risk insurance / riesgo). */
    ON_SALE_PRICE
}
