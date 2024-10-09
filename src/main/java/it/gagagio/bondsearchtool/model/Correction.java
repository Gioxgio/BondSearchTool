package it.gagagio.bondsearchtool.model;

import lombok.Builder;

@Builder
public record Correction(String isin, Integer coupon) {
    public static Correction fromCsvRow(final String[] args) {
        return new Correction(args[0], Integer.valueOf(args[1]));
    }
}