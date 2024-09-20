package it.gagagio.bondsearchtool.model;

import lombok.Builder;

import java.time.Instant;

@Builder
public record Bond(String isin, String name, String market, Instant maturityAt, boolean perpetual, Integer coupon,
                   Integer lastPrice, BondCountry country, BondType type) {
}