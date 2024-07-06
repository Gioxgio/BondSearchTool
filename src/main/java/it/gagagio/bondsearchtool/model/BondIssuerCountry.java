package it.gagagio.bondsearchtool.model;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum BondIssuerCountry {
    AT("REPUBLIC OF AUSTRIA");

    private final String name;

    public static BondIssuerCountry valueFrom(final String s) {
        return Arrays.stream(values())
                .filter(v -> v.name.equals(s))
                .findFirst().orElse(null);
    }
}
