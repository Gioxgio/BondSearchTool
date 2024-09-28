package it.gagagio.bondsearchtool.data.repository;

import it.gagagio.bondsearchtool.data.entity.BondEntity;
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

    List<BondEntity> findByErrorIsNullAndLastModifiedAtLessThan(final Instant instant, final Limit limit);

    List<BondEntity> findByTypeIsNullOrCountryIsNullOrMaturityAtIsNullAndPerpetualIsFalseOrCouponIsNullAndErrorIsNull(final Limit limit);

    // @Query("SELECT b FROM bond b WHERE b.type = 'GOVERNMENT' AND b.yieldToMaturity IS NOT NULL AND b.yieldToMaturity > 0")
    @Query("SELECT b FROM bond b")
    List<BondEntity> findValidGovernmentBonds();

    int removeByIsinNotIn(final Collection<String> isins);
}