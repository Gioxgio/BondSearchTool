package it.gagagio.bondsearchtool.controller.mapper;

import it.gagagio.bondsearchtool.controller.response.BondResponse;
import it.gagagio.bondsearchtool.controller.response.CountryResponse;
import it.gagagio.bondsearchtool.controller.response.MarketResponse;
import it.gagagio.bondsearchtool.data.entity.BondEntity;
import it.gagagio.bondsearchtool.model.BondCountry;
import it.gagagio.bondsearchtool.model.BondMarket;
import org.springframework.stereotype.Component;

@Component
public class BondResponseMapper {

    public BondResponse from(final BondEntity bond) {
        return BondResponse.builder()
                .isin(bond.getIsin())
                .name(bond.getName())
                .market(from(bond.getMarket()))
                .maturityAt(bond.getMaturityAt())
                .perpetual(bond.isPerpetual())
                .coupon(bond.getCoupon())
                .lastPrice(bond.getLastPrice())
                .country(from(bond.getCountry()))
                .yieldToMaturity(bond.getYieldToMaturity())
                .type(bond.getType())
                .build();
    }

    public CountryResponse from(final BondCountry country) {
        return new CountryResponse(country.name(), country.getName());
    }

    public MarketResponse from(final BondMarket market) {
        return new MarketResponse(market.name(), market.getName());
    }
}
