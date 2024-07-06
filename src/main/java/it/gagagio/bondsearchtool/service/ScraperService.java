package it.gagagio.bondsearchtool.service;

import it.gagagio.bondsearchtool.data.entity.BondEntityMapper;
import it.gagagio.bondsearchtool.data.repository.BondRepository;
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
    private final BondEntityMapper bondEntityMapper;
    private final BondRepository bondRepository;

    public int refresh(final BondIssuerRegion region, final BondIssuerType type) {

        val bonds = euronext.refresh(region, type);
        val bondEntities = bonds.stream().map(bondEntityMapper::getBondEntityFromBond).toList();

        return bondRepository.saveAllAndFlush(bondEntities).size();
    }
}
