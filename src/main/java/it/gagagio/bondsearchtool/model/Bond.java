package it.gagagio.bondsearchtool.model;

import it.gagagio.bondsearchtool.euronext.model.BondIssuerCountry;
import it.gagagio.bondsearchtool.euronext.model.BondIssuerRegion;
import lombok.Builder;

import java.time.Instant;

@Builder
public record Bond(String isin, String name, String market, Instant maturityAt, int coupon, int lastPrice,
                   BondIssuerCountry country, BondIssuerRegion region, BondType type) {
}