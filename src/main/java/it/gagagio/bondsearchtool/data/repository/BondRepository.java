package it.gagagio.bondsearchtool.data.repository;

import it.gagagio.bondsearchtool.data.entity.BondEntity;
import it.gagagio.bondsearchtool.model.BondCountry;
import it.gagagio.bondsearchtool.model.BondMarket;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface BondRepository extends JpaRepository<BondEntity, String> {

    @Query("SELECT DISTINCT(b.isin) FROM bond b")
    Set<String> findAllIsins();

    List<BondEntity> findByErrorIsNullAndLastModifiedAtLessThanAndPerpetualIsFalse(final Instant instant, final Limit limit);

    Set<BondEntity> findByIsinIn(final Collection<String> isins);

    @Query(BondQueries.BONDS_TO_UPDATE)
    List<BondEntity> findBondsToUpdate(final Limit limit);

    @Query(BondQueries.VALID_BONDS)
    List<BondEntity> findValidBonds();

    @Query(BondQueries.VALID_BONDS_COUNTRIES)
    List<BondCountry> findValidBondsCountries();

    @Query(BondQueries.VALID_BONDS_MARKETS)
    List<BondMarket> findValidBondsMarkets();

    int removeByIsinNotIn(final Collection<String> isins);
}