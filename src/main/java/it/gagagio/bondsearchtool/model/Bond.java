package it.gagagio.bondsearchtool.model;

import it.gagagio.bondsearchtool.euronext.model.BondIssuerRegion;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record Bond(String id, String name, String market, LocalDate maturityAt, int coupon, int lastPrice,
                   BondIssuerCountry country, BondIssuerRegion region) {
}