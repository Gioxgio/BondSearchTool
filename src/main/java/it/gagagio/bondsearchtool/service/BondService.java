package it.gagagio.bondsearchtool.service;

import it.gagagio.bondsearchtool.borsaitaliana.BorsaItaliana;
import it.gagagio.bondsearchtool.data.entity.BondEntity;
import it.gagagio.bondsearchtool.data.repository.BondRepository;
import it.gagagio.bondsearchtool.euronext.Euronext;
import it.gagagio.bondsearchtool.model.BondType;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class BondService {

    private final BondRepository bondRepository;
    private final BorsaItaliana borsaItaliana;
    private final Euronext euronext;

    public List<BondEntity> getBonds() {
        return bondRepository.findValidCorporateBonds();
    }

    public void calculateYieldToMaturity() {

        val bonds = bondRepository.findBondsWithWrongYieldToMaturity(Set.of(/*"ETLX", "MOTX",*/ "XMOT"), Limit.of(100));

        bonds.forEach(this::calculateYieldToMaturity);

        bondRepository.saveAll(bonds);
    }

    public void enrichBonds() {

        val bonds = bondRepository.findByTypeIsNullOrTypeEquals(BondType.OTHERS, Limit.of(100));

        bonds.forEach(euronext::enrichBond);

        bondRepository.saveAll(bonds);
    }

    private void calculateYieldToMaturity(final BondEntity bond) {

        val yieldToMaturity = borsaItaliana.getYieldToMaturity(bond.getIsin(), bond.getMarket());

        if (yieldToMaturity != 0) {
            bond.setYieldToMaturity(yieldToMaturity);
        }
    }
}
