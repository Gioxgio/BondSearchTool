package it.gagagio.bondsearchtool.model;

import it.gagagio.bondsearchtool.euronext.model.EuronextIssuerCountry;
import lombok.Builder;

import java.time.Instant;

@Builder
public record Bond(String isin, String name, String market, Instant maturityAt, Integer coupon, Integer lastPrice,
                   EuronextIssuerCountry country, BondType type) {
}