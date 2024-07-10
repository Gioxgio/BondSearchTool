package it.gagagio.bondsearchtool.service;

import it.gagagio.bondsearchtool.borsaitaliana.BorsaItaliana;
import it.gagagio.bondsearchtool.data.entity.BondEntity;
import it.gagagio.bondsearchtool.data.repository.BondRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class BondService {

    private final BondRepository bondRepository;
    private final BorsaItaliana borsaItaliana;

    public void calculateYieldToMaturity() {

        val bonds = bondRepository.findByYieldToMaturityIsNullAndMarketIn(Set.of("MOTX", "XMOT"), Limit.of(100));

        bonds.forEach(this::calculateYieldToMaturity);

        bondRepository.saveAll(bonds);
    }

    private void calculateYieldToMaturity(final BondEntity bond) {

        val yieldToMaturity = borsaItaliana.getYieldToMaturity(bond.getIsin(), bond.getMarket());

        bond.setYieldToMaturity(yieldToMaturity);
    }
}
