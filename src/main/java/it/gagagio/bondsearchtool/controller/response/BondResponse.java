package it.gagagio.bondsearchtool.controller.response;

import it.gagagio.bondsearchtool.model.BondType;
import lombok.Builder;

import java.time.Instant;

@Builder
public record BondResponse(String isin, String name, String market, Instant maturityAt, boolean perpetual,
                           Integer coupon, Integer lastPrice, CountryResponse country, Integer yieldToMaturity,
                           BondType type) {
}