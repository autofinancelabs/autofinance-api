package com.autofinance.api.vehicleoffers.interfaces.acl;

import java.math.BigDecimal;

/** Published, minimal view of a vehicle offer for other contexts (primitives only — no domain types). */
public record VehicleOfferSummary(BigDecimal salePrice, String currency) {
}
