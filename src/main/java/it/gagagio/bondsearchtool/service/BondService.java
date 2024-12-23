package it.gagagio.bondsearchtool.service;

import it.gagagio.bondsearchtool.data.entity.BondEntity;
import it.gagagio.bondsearchtool.data.repository.BondRepository;
import it.gagagio.bondsearchtool.euronext.Euronext;
import it.gagagio.bondsearchtool.model.BondCountry;
import it.gagagio.bondsearchtool.model.BondMarket;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BondService {

    private final BondRepository bondRepository;
    private final Euronext euronext;

    public List<BondEntity> getBonds() {
        return bondRepository.findValidBonds();
    }

    public List<BondCountry> getCountries() {
        return bondRepository.findValidBondsCountries();
    }

    public List<BondMarket> getMarkets() {
        return bondRepository.findValidBondsMarkets();
    }

    public int updateDynamicFields(final int pageSize) {

        val bonds = bondRepository.findByErrorIsNullAndLastModifiedAtLessThanAndPerpetualIsFalse(Instant.now().truncatedTo(ChronoUnit.DAYS), Limit.of(pageSize));

        bonds.forEach(this::updateDynamicFields);

        bondRepository.saveAll(bonds);

        return bonds.size();
    }

    public void updateDynamicFields(final BondEntity bond) {
        euronext.updateDynamicFields(bond);
    }

    public int updateStaticFields(final int pageSize) {

        val bonds = bondRepository.findBondsToUpdate(Limit.of(pageSize));

        bonds.forEach(euronext::updateStaticFields);

        bondRepository.saveAll(bonds);

        return bonds.size();
    }
}
