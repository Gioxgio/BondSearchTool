package it.gagagio.bondsearchtool.service;

import it.gagagio.bondsearchtool.data.entity.BondEntityMapper;
import it.gagagio.bondsearchtool.data.repository.BondRepository;
import it.gagagio.bondsearchtool.euronext.Euronext;
import it.gagagio.bondsearchtool.model.Bond;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScraperService {

    private final Euronext euronext;
    private final BondEntityMapper bondEntityMapper;
    private final BondRepository bondRepository;

    @Transactional
    public void updateBondList() {

        log.info("Getting bonds from Euronext");
        val bondsOptional = euronext.getBondsList();
        if (bondsOptional.isEmpty()) {
            return;
        }
        val bonds = bondsOptional.get();
        val newIsins = bonds.stream().map(Bond::isin).toList();

        log.info("Getting existing bonds");
        val existingIsins = bondRepository.findAllIsins();

        val newBonds = bonds.stream().filter(b -> !existingIsins.contains(b.isin())).toList();
        val newBondEntities = newBonds.stream().map(bondEntityMapper::fromBond).toList();

        val removed = bondRepository.removeByIsinNotIn(newIsins);
        log.info("Removed {} outdated bonds", removed);

        log.info("Saving {} new bonds", newBondEntities.size());
        bondRepository.saveAllAndFlush(newBondEntities);
    }
}
