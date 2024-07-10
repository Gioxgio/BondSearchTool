package it.gagagio.bondsearchtool.data.repository;

import it.gagagio.bondsearchtool.data.entity.BondEntity;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface BondRepository extends JpaRepository<BondEntity, String> {

    List<BondEntity> findByYieldToMaturityIsNullAndMarketIn(final Collection<String> markets, final Limit limit);
}
