package it.gagagio.bondsearchtool.euronext.model;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum BondIssuerCountry {
    AT("REPUBLIC OF AUSTRIA"),
    BE("KINGDOM OF BELGIUM"),
    FR("REPUBLIC OF FRANCE"),
    NL("KINGDOM OF THE NETHERLANDS"),
    NO("KINGDOM OF NORWAY"),
    PT("REPUBLIC OF PORTUGAL"),
    UK("UNITED KINGDOM"),
    OTHERS("OTHERS");

    private final String name;

    public static BondIssuerCountry valueFrom(final String s) {
        return Arrays.stream(values())
                .filter(v -> v.name.equals(s))
                .findFirst().orElse(OTHERS);
    }
}
