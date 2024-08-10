package it.gagagio.bondsearchtool.data.repository;

import it.gagagio.bondsearchtool.data.entity.BondEntity;
import it.gagagio.bondsearchtool.model.BondType;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface BondRepository extends JpaRepository<BondEntity, String> {

    @Query("SELECT DISTINCT(b.isin) FROM bond b")
    Set<String> findAllIsins();

    List<BondEntity> findByYieldToMaturityIsNullAndMarketIn(final Collection<String> markets, final Limit limit);

    List<BondEntity> findByTypeIsNullOrTypeEquals(final BondType type, final Limit limit);

    int removeByIsinIn(final Collection<String> isins);
}

