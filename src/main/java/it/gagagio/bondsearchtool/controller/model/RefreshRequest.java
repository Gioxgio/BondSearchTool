package it.gagagio.bondsearchtool.controller.model;

import it.gagagio.bondsearchtool.euronext.model.BondIssuerRegion;
import it.gagagio.bondsearchtool.euronext.model.BondIssuerType;

public record RefreshRequest(BondIssuerRegion bondIssuerRegion, BondIssuerType bondIssuerType) {
}
