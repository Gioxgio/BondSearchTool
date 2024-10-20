package it.gagagio.bondsearchtool.controller.response;

import it.gagagio.bondsearchtool.model.BondType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
public record BondResponse(String isin, String name, MarketResponse market, Instant maturityAt, boolean perpetual,
                           BigDecimal coupon, BigDecimal lastPrice, CountryResponse country, BigDecimal yieldToMaturity,
                           BondType type) {
}