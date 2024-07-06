package it.gagagio.bondsearchtool.data.entity;

import it.gagagio.bondsearchtool.model.Bond;
import org.springframework.stereotype.Component;

@Component
public class BondEntityMapper {

    public BondEntity getBondEntityFromBond(final Bond bond) {

        return BondEntity.builder()
                .id(bond.id())
                .name(bond.name())
                .market(bond.market())
                .maturityAt(bond.maturityAt())
                .coupon(bond.coupon())
                .lastPrice(bond.lastPrice())
                .country(bond.country())
                .region(bond.region())
                .build();
    }
}