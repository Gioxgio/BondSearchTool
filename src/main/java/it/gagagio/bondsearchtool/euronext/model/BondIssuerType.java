package it.gagagio.bondsearchtool.euronext.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BondIssuerType {
    GOVERNMENT(1),
    OTHERS(4);

    private final int id;
}
