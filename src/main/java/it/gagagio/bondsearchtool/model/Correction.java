package it.gagagio.bondsearchtool.model;

import lombok.Builder;

@Builder
public record Correction(String isin, BondCountry country, Integer coupon) {
    public static Correction fromCsvRow(final String[] args) {
        return new Correction(args[0], BondCountry.valueOf(args[1]), Integer.valueOf(args[2]));
    }
}