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

    @Query("SELECT b FROM bond b WHERE b.market IN (:markets) AND (b.yieldToMaturity IS NULL OR b.yieldToMaturity = 0 OR b.lastModifiedAt < NOW() - '25' HOUR)")
    List<BondEntity> findBondsWithWrongYieldToMaturity(final Collection<String> markets, final Limit limit);

    List<BondEntity> findByTypeIsNullOrTypeEquals(final BondType type, final Limit limit);

    @Query("SELECT b FROM bond b WHERE b.type = 'GOVERNMENT' AND b.yieldToMaturity IS NOT NULL AND b.yieldToMaturity > 0")
    List<BondEntity> findValidCorporateBonds();

    void removeByIsinIn(final Collection<String> isins);
}