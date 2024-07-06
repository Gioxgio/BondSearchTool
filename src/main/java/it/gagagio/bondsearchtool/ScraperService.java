package it.gagagio.bondsearchtool;

import it.gagagio.bondsearchtool.euronext.Euronext;
import it.gagagio.bondsearchtool.euronext.model.BondIssuerRegion;
import it.gagagio.bondsearchtool.euronext.model.BondIssuerType;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScraperService {

    private final Euronext euronext;

    public int refresh(final BondIssuerRegion region, final BondIssuerType type) {
        val bonds = euronext.refresh(region, type);

        // TODO persist entities and return

        return bonds.size();
    }
}
