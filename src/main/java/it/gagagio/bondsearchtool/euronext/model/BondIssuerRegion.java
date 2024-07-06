package it.gagagio.bondsearchtool.euronext.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BondIssuerRegion {
    EUROPE(1),
    EMEA(2),
    C_B_PR(4),
    CANADA(7),
    US(8);

    private final int id;
}
