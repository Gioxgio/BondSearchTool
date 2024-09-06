package it.gagagio.bondsearchtool.service;

import it.gagagio.bondsearchtool.data.entity.BondEntity;
import it.gagagio.bondsearchtool.data.entity.BondEntityMapper;
import it.gagagio.bondsearchtool.data.repository.BondRepository;
import it.gagagio.bondsearchtool.euronext.Euronext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScraperService {

    private final Euronext euronext;
    private final BondEntityMapper bondEntityMapper;
    private final BondRepository bondRepository;

    @Transactional
    public void refresh() {

        val bonds = euronext.refresh();
        val bondEntities = bonds.stream().map(bondEntityMapper::fromBond).toList();

        val newIsins = bondEntities.stream().map(BondEntity::getIsin).toList();
        val existingIsins = bondRepository.findAllIsins();

        val newBondEntities = bondEntities.stream().filter(b -> !existingIsins.contains(b.getIsin())).toList();
        val outdatedBondEntities = existingIsins.stream().filter(i -> !newIsins.contains(i)).toList();

        bondRepository.removeByIsinIn(outdatedBondEntities);

        bondRepository.saveAllAndFlush(newBondEntities);
    }
}
